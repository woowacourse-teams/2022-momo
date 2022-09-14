package com.woowacourse.momo.storage.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;
import com.woowacourse.momo.storage.exception.GroupImageErrorCode;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupImageService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupImageRepository groupImageRepository;
    private final StorageService storageService;

    @Transactional
    public void init(Group group) {
        String defaultImageName = group.getCategory()
                .getDefaultImageName();
        GroupImage groupImage = new GroupImage(group, defaultImageName);

        groupImageRepository.save(groupImage);
    }

    @Transactional
    public void save(Long memberId, Long groupId, MultipartFile multipartFile) {
        Member member = memberFindService.findMember(memberId);
        Group group = groupFindService.findGroup(groupId);
        validateMemberIsNotHost(member, group);

        String savedImageName = generateImageName(multipartFile);

        GroupImage groupImage = new GroupImage(group, savedImageName);

        groupImageRepository.save(groupImage);
        storageService.save(savedImageName, multipartFile);
    }

    private void validateMemberIsNotHost(Member member, Group group) {
        if (group.isNotHost(member)) {
            throw new MomoException(GroupImageErrorCode.MEMBER_IS_NOT_HOST);
        }
    }

    private String generateImageName(MultipartFile multipartFile) {
        String imageName = multipartFile.getOriginalFilename();
        String extension = imageName.substring(imageName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        return uuid + extension;
    }
}
