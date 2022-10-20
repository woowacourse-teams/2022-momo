package com.woowacourse.momo.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.domain.participant.ParticipantRepository;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;
import com.woowacourse.momo.member.event.MemberDeleteEvent;
import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;
import com.woowacourse.momo.member.service.dto.response.MemberResponseAssembler;
import com.woowacourse.momo.member.service.dto.response.MyInfoResponse;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberFindService memberFindService;
    private final PasswordEncoder passwordEncoder;
    private final GroupFindService groupFindService;
    private final TokenRepository tokenRepository;
    private final ParticipantRepository participantRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Long signUp(SignUpRequest request) {
        UserId userId = UserId.momo(request.getUserId());
        UserName userName = UserName.from(request.getName());
        Password password = Password.encrypt(request.getPassword(), passwordEncoder);
        validateUserIdIsNotDuplicated(userId);
        validateUserNameIsNotDuplicated(userName);

        Member member = new Member(userId, password, userName);
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void validateUserIdIsNotDuplicated(UserId userId) {
        if (memberRepository.existsByUserId(userId)) {
            throw new MemberException(MemberErrorCode.SIGNUP_USER_ID_DUPLICATED);
        }
    }

    private void validateUserNameIsNotDuplicated(UserName userName) {
        if (memberRepository.existsByUserName(userName)) {
            throw new MemberException(MemberErrorCode.SIGNUP_USER_NAME_DUPLICATED);
        }
    }

    public MyInfoResponse findById(Long id) {
        Member member = memberFindService.findMember(id);

        return MemberResponseAssembler.myInfoResponse(member);
    }

    @Transactional
    public void deleteById(Long id) {
        Member member = memberFindService.findMember(id);
        leaveProgressingGroup(member);
        tokenRepository.deleteByMemberId(member.getId());
        applicationEventPublisher.publishEvent(new MemberDeleteEvent(id));
        member.delete();
    }

    private void leaveProgressingGroup(Member member) {
        List<Group> progressingGroups = groupFindService.findParticipatedGroups(member)
                .stream()
                .filter(group -> !group.isFinishedRecruitment())
                .collect(Collectors.toList());
        validateMemberIsNotHost(member, progressingGroups);

        participantRepository.deleteAllByMemberIdInGroups(member.getId(), progressingGroups);
    }

    private void validateMemberIsNotHost(Member member, List<Group> groups) {
        if (isHost(member, groups)) {
            throw new MomoException(MemberErrorCode.MEMBER_DELETED_EXIST_IN_PROGRESS_GROUP);
        }
    }

    private boolean isHost(Member member, List<Group> groups) {
        return groups.stream()
                .anyMatch(group -> group.isHost(member));
    }

    @Transactional
    public void updatePassword(Long id, ChangePasswordRequest request) {
        Member member = memberFindService.findMember(id);

        confirmPassword(member, request.getOldPassword());
        member.changePassword(request.getNewPassword(), passwordEncoder);
    }

    @Transactional
    public void updateName(Long id, ChangeNameRequest request) {
        UserName userName = UserName.from(request.getName());
        validateUserNameIsNotDuplicated(userName);

        Member member = memberFindService.findMember(id);
        member.changeUserName(request.getName());
    }

    private void confirmPassword(Member member, String password) {
        String encryptedPassword = passwordEncoder.encrypt(password);
        if (member.isNotSamePassword(encryptedPassword)) {
            throw new MemberException(MemberErrorCode.MEMBER_WRONG_PASSWORD);
        }
    }
}
