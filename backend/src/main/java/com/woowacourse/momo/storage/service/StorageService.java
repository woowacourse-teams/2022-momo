package com.woowacourse.momo.storage.service;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.MemberFindService;
import com.woowacourse.momo.storage.domain.image.GroupImage;
import com.woowacourse.momo.storage.domain.image.GroupImageRepository;
import com.woowacourse.momo.storage.domain.image.MemberImage;
import com.woowacourse.momo.storage.domain.image.MemberImageRepository;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final GroupFindService groupFindService;
    private final MemberFindService memberFindService;
    private final ImageFileManager imageFileManager;
    private final GroupImageRepository groupImageRepository;
    private final MemberImageRepository memberImageRepository;

    public String saveGroupImage(Long groupId, MultipartFile imageFile) {
        String savedFileName = imageFileManager.save(ImageFileManager.PATH_PREFIX + "group/", imageFile);
        Group group = groupFindService.findGroup(groupId);

        groupImageRepository.findByGroupId(group.getId())
                .ifPresentOrElse(
                        groupImage -> groupImage.updateFileName(savedFileName),
                        () -> groupImageRepository.save(new GroupImage(group, savedFileName))
                );

        return savedFileName;
    }

    public String saveMemberImage(Long memberId, MultipartFile imageFile) {
        String savedFileName = imageFileManager.save(ImageFileManager.PATH_PREFIX + "member/", imageFile);
        Member member = memberFindService.findMember(memberId);

        memberImageRepository.findByMemberId(member.getId())
                .ifPresentOrElse(
                        memberImage -> memberImage.updateFileName(savedFileName),
                        () -> memberImageRepository.save(new MemberImage(member, savedFileName))
                );

        return savedFileName;
    }

    public byte[] loadMemberImage(Long memberId) {
        AtomicReference<String> fileName = new AtomicReference<>();
        memberImageRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        memberImage -> fileName.set(memberImage.getFileName()),
                        () -> fileName.set("default.png")
                );

        return imageFileManager.load(ImageFileManager.PATH_PREFIX + "member/", fileName.get());
    }

    public byte[] loadGroupImage(Long groupId) {
        AtomicReference<String> fileName = new AtomicReference<>();

        groupImageRepository.findByGroupId(groupId)
                .ifPresentOrElse(
                        groupImage -> fileName.set(groupImage.getFileName()),
                        () -> fileName.set("default.png")
                );

        return imageFileManager.load(ImageFileManager.PATH_PREFIX + "group/", fileName.get());
    }
}
