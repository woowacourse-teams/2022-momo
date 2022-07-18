package com.woowacourse.momo.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.domain.group.GroupRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.dto.response.MemberResponse;
import com.woowacourse.momo.member.dto.response.MemberResponseAssembler;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GroupParticipantService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void participate(Long groupId, Long memberId) {
        Group group = findGroupById(groupId);
        Member participant = findMemberById(memberId);

        group.participate(participant);
    }

    public List<MemberResponse> findParticipants(Long groupId) {
        Group group = findGroupById(groupId);
        List<Member> participants = group.getParticipants();

        return MemberResponseAssembler.memberResponses(participants);
    }

    private Group findGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
