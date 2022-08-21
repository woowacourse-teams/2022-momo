package com.woowacourse.momo.participant.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_FINISHED;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_JOIN_BY_HOST;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_RE_PARTICIPATE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Participants {

    private static final int NONE_PARTICIPANT = 1;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> value = new ArrayList<>();

    public Participants(Group group, Member member) {
        value.add(new Participant(group, member));
    }

    public void participate(Group group, Member member) {
        validateGroupIsProceeding(group);
        validateMemberIsNotHost(group, member);
        validateMemberIsNotParticipant(member);
        value.add(new Participant(group, member));
    }

    public int size() {
        return value.size();
    }

    public boolean isExist() {
        return value.size() > NONE_PARTICIPANT;
    }

    public boolean isParticipant(Member member) {
        return getValue().contains(member);
    }

    private void validateGroupIsProceeding(Group group) {
        if (group.isFinishedRecruitment()) {
            throw new MomoException(PARTICIPANT_FINISHED);
        }
    }

    private void validateMemberIsNotHost(Group group, Member member) {
        if (group.isHost(member)) {
            throw new MomoException(PARTICIPANT_JOIN_BY_HOST);
        }
    }

    private void validateMemberIsNotParticipant(Member member) {
        if (isParticipant(member)) {
            throw new MomoException(PARTICIPANT_RE_PARTICIPATE);
        }
    }

    public List<Member> getValue() {
        return value.stream()
                .map(Participant::getMember)
                .collect(Collectors.toList());
    }
}