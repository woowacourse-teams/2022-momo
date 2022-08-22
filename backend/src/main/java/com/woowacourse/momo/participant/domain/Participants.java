package com.woowacourse.momo.participant.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_CAPACITY_IS_OVER_SIZE;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_FINISHED;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_JOIN_BY_HOST;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_RE_PARTICIPATE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.group.Capacity;
import com.woowacourse.momo.group.domain.group.Group;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Participants {

    private static final int NONE_PARTICIPANT = 1;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> participants = new ArrayList<>();

    @Embedded
    private Capacity capacity;

    public Participants(Group group, Capacity capacity) {
        participants.add(new Participant(group, group.getHost()));
        this.capacity = capacity;
    }

    public void participate(Group group, Member member) {
        validateGroupIsProceeding(group);
        validateMemberIsNotHost(group, member);
        validateMemberIsNotParticipant(member);
        participants.add(new Participant(group, member));
    }

    public void update(Capacity capacity) {
        this.capacity = capacity;
        validateCapacityIsOverNumberOfParticipants();
    }

    public boolean isFull() {
        return capacity.isFull(participants.size());
    }

    public boolean isExist() {
        return participants.size() > NONE_PARTICIPANT;
    }

    public boolean isParticipant(Member member) {
        return getParticipants().contains(member);
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

    private void validateCapacityIsOverNumberOfParticipants() {
        if (capacity.isUnder(participants.size())) {
            throw new MomoException(PARTICIPANT_CAPACITY_IS_OVER_SIZE);
        }
    }

    public List<Member> getParticipants() {
        return participants.stream()
                .map(Participant::getMember)
                .collect(Collectors.toList());
    }
}
