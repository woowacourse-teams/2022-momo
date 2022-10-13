package com.woowacourse.momo.storage.service;

import static com.woowacourse.momo.storage.exception.GroupImageErrorCode.GROUP_IMAGE_IS_NOT_EXIST;

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
import com.woowacourse.momo.storage.exception.GroupImageException;
import com.woowacourse.momo.storage.support.ImageConnector;
import com.woowacourse.momo.storage.support.ImageProvider;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupImageService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;
    private final GroupImageRepository groupImageRepository;
    private final ImageConnector imageConnector;
    private final ImageProvider imageProvider;

    @Transactional
    public void save(Long groupId, String defaultImageName) {
        GroupImage groupImage = new GroupImage(groupId, defaultImageName);

        groupImageRepository.save(groupImage);
    }

    @Transactional
    public String update(Long memberId, Long groupId, MultipartFile multipartFile) {
        validateMemberIsHost(memberId, groupId);

        String fullPathOfSavedImage = imageConnector.requestImageSave(imageProvider.getSavedGroupPath(), multipartFile);
        String savedImageName = parseSavedImageName(fullPathOfSavedImage);

        updateGroupImage(groupId, savedImageName);
        return fullPathOfSavedImage;
    }

    @Transactional
    public void init(Long memberId, Long groupId) {
        validateMemberIsHost(memberId, groupId);

        Group group = groupFindService.findGroup(groupId);
        String defaultImageName = group.getCategory().getDefaultImageName();

        updateGroupImage(groupId, defaultImageName);
    }

    private void validateMemberIsHost(Long memberId, Long groupId) {
        Member member = memberFindService.findMember(memberId);
        Group group = groupFindService.findGroup(groupId);
        if (group.isNotHost(member)) {
            throw new MomoException(GroupImageErrorCode.MEMBER_IS_NOT_HOST);
        }
    }

    private String parseSavedImageName(String fullPathOfSavedImage) {
        int startIndexImageName = fullPathOfSavedImage.indexOf(imageProvider.getSavedGroupPath())
                + imageProvider.getSavedGroupPath().length() + 1;
        return fullPathOfSavedImage.substring(startIndexImageName);
    }

    private void updateGroupImage(Long groupId, String savedImageName) {
        GroupImage existedGroupImage = groupImageRepository.findByGroupId(groupId)
                        .orElseThrow(() -> new GroupImageException(GROUP_IMAGE_IS_NOT_EXIST));
        existedGroupImage.update(savedImageName);
    }
}
