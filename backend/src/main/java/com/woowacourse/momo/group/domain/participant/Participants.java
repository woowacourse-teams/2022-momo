package com.woowacourse.momo.group.domain.participant;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_HOST;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_NOT_PARTICIPANT;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_PARTICIPANTS_FULL;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_RE_PARTICIPATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Participants {

    @ManyToOne
    @JoinColumn
    private Member host;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> participants = new ArrayList<>();

    @Embedded
    private Capacity capacity;

    public Participants(Member host, Capacity capacity) {
        this.host = host;
        this.capacity = capacity;
    }

    public void updateCapacity(Capacity capacity) {
        validateCapacityIsOverNumberOfParticipants(capacity);
        this.capacity = capacity;
    }

    public void participate(Group group, Member member) {
        validateMemberIsNotHost(member);
        validateMemberIsNotParticipated(member);
        validateParticipantsNotYetFull();
        participants.add(new Participant(group, member));
    }

    public void leave(Member member) {
        validateMemberIsNotHost(member);
        participants.remove(getParticipant(member));
    }

    private Participant getParticipant(Member member) {
        return participants.stream()
                .filter(participant -> participant.isSameMember(member))
                .findAny()
                .orElseThrow(() -> new MomoException(PARTICIPANT_LEAVE_NOT_PARTICIPANT));
    }

    public boolean isNotEmpty() {
        return !participants.isEmpty();
    }

    public boolean isFull() {
        return capacity.isSame(getSize());
    }

    private boolean contains(Member member) {
        return getParticipants().contains(member);
    }

    public boolean isHost(Member member) {
        return host.isSameUserId(member);
    }

    private void validateMemberIsNotHost(Member member) {
        if (host.equals(member)) {
            throw new MomoException(PARTICIPANT_LEAVE_HOST);
        }
    }

    private void validateMemberIsNotParticipated(Member member) {
        if (contains(member)) {
            throw new MomoException(PARTICIPANT_RE_PARTICIPATE);
        }
    }

    private void validateParticipantsNotYetFull() {
        if (isFull()) {
            throw new MomoException(PARTICIPANT_PARTICIPANTS_FULL);
        }
    }

    private void validateCapacityIsOverNumberOfParticipants(Capacity capacity) {
        if (capacity.isUnder(getSize())) {
            throw new MomoException(ErrorCode.PARTICIPANT_CAPACITY_IS_OVER_SIZE);
        }
    }

    private int getSize() {
        return getParticipants().size();
    }

    public List<Member> getParticipants() {
        List<Member> members = participants.stream()
                .map(Participant::getMember)
                .collect(Collectors.toUnmodifiableList());

        return Stream.of(List.of(host), members)
                .flatMap(Collection::stream)
                .collect(Collectors.toUnmodifiableList());
    }
}
