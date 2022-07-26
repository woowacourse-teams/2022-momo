package com.woowacourse.momo.participant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.group.service.GroupFindService;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.dto.response.MemberResponse;
import com.woowacourse.momo.member.dto.response.MemberResponseAssembler;
import com.woowacourse.momo.member.service.MemberFindService;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ParticipantService {

    private final MemberFindService memberFindService;
    private final GroupFindService groupFindService;

    @Transactional
    public void participate(Long groupId, Long memberId) {
        Group group = groupFindService.findGroup(groupId);
        List<Member> participants = group.getParticipants();
        Member member = memberFindService.findMember(memberId);

        if (group.isSameHost(member) || participants.contains(member)) {
            throw new IllegalArgumentException("이미 참여한 모임입니다.");
        }

        group.participate(member);
    }

    public List<MemberResponse> findParticipants(Long groupId) {
        Group group = groupFindService.findGroup(groupId);
        List<Member> participants = group.getParticipants();

        return MemberResponseAssembler.memberResponses(participants);
    }
}
