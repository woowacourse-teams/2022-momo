package com.woowacourse.momo.storage.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;
import com.woowacourse.momo.storage.domain.GroupImage;
import com.woowacourse.momo.storage.domain.GroupImageRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupImageService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupImageRepository groupImageRepository;

    @Transactional
    public void save(Long memberId, Long groupId, MultipartFile multipartFile) {
        Member member = memberFindService.findMember(memberId);
        Group group = groupFindService.findGroup(groupId);
        if (group.isNotHost(member)) {
            throw new MomoException(ErrorCode.MEMBER_IS_NOT_HOST);
        }

        String imageName = multipartFile.getOriginalFilename();
        String extension = imageName.substring(imageName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String savedImageName = uuid + extension;

        GroupImage groupImage = new GroupImage(group, savedImageName);

        groupImageRepository.save(groupImage);
    }
}
