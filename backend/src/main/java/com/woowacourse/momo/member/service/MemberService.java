package com.woowacourse.momo.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;
import com.woowacourse.momo.member.service.dto.response.MyInfoResponse;
import com.woowacourse.momo.participant.domain.ParticipantRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberFindService memberFindService;
    private final PasswordEncoder passwordEncoder;
    private final GroupFindService groupFindService;
    private final ParticipantRepository participantRepository;
    private final TokenRepository tokenRepository;

    public MyInfoResponse findById(Long id) {
        Member member = memberFindService.findMember(id);

        return MemberResponseAssembler.myInfoResponse(member);
    }

    @Transactional
    public void deleteById(Long id) {
        Member member = memberFindService.findMember(id);
        leaveProgressingGroup(member);
        tokenRepository.deleteByMemberId(member.getId());
        member.delete();
    }

    private void leaveProgressingGroup(Member member) {
        List<Group> progressingGroups = groupFindService.findParticipatedGroups(member)
                .stream()
                .filter(group -> !group.isEnd())
                .collect(Collectors.toList());
        validateMemberNotHost(member, progressingGroups);
        progressingGroups.forEach(
                group -> participantRepository.deleteByGroupIdAndMemberId(group.getId(), member.getId()));
    }

    private void validateMemberNotHost(Member member, List<Group> groups) {
        if (isMemberHost(member, groups)) {
            throw new MomoException(ErrorCode.MEMBER_DELETED_EXIST_IN_PROGRESS_GROUP);
        }
    }

    private boolean isMemberHost(Member member, List<Group> groups) {
        return groups.stream()
                .anyMatch(group -> group.isHost(member));
    }

    @Transactional
    public void updatePassword(Long id, ChangePasswordRequest request) {
        Member member = memberFindService.findMember(id);

        String encryptedNewPassword = passwordEncoder.encrypt(request.getNewPassword());
        confirmPassword(member, request.getOldPassword());
        member.changePassword(encryptedNewPassword);
    }

    @Transactional
    public void updateName(Long id, ChangeNameRequest request) {
        Member member = memberFindService.findMember(id);

        member.changeName(request.getName());
    }

    private void confirmPassword(Member member, String password) {
        String encryptedPassword = passwordEncoder.encrypt(password);
        if (member.isNotSamePassword(encryptedPassword)) {
            throw new MomoException(ErrorCode.MEMBER_WRONG_PASSWORD);
        }
    }
}
