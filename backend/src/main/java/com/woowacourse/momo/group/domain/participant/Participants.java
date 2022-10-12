package com.woowacourse.momo.group.domain.participant;

import static com.woowacourse.momo.group.exception.GroupErrorCode.ALREADY_PARTICIPANTS_SIZE_FULL;
import static com.woowacourse.momo.group.exception.GroupErrorCode.CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_IS_HOST;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_IS_NOT_PARTICIPANT;
import static com.woowacourse.momo.group.exception.GroupErrorCode.MEMBER_IS_PARTICIPANT;

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

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Participants {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member host;

    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private final List<Participant> participants = new ArrayList<>();

    @Embedded
    private Capacity capacity;

    public Participants(Member host, Capacity capacity) {
        this.host = host;
        this.capacity = capacity;
    }

    public void participate(Group group, Member member) {
        validateMemberCanParticipate(member);
        participants.add(new Participant(group, member));
    }

    public void remove(Member member) {
        validateMemberCanLeave(member);
        removeParticipant(member);
    }

    private void removeParticipant(Member member) {
        participants.stream()
                .filter(participant -> participant.isSameMember(member))
                .findAny()
                .ifPresent(participants::remove);
    }

    public void updateCapacity(Capacity capacity) {
        validateCapacityIsOverParticipantsSize(capacity);
        this.capacity = capacity;
    }

    private void validateMemberCanLeave(Member member) {
        validateMemberIsNotHost(member);
        validateMemberIsParticipant(member);
    }

    private void validateMemberCanParticipate(Member member) {
        validateMemberIsNotHost(member);
        validateMemberIsNotParticipant(member);
        validateParticipantsNotYetFull();
    }

    private void validateMemberIsNotHost(Member member) {
        if (host.equals(member)) {
            throw new GroupException(MEMBER_IS_HOST);
        }
    }

    private void validateMemberIsParticipant(Member member) {
        if (!contains(member)) {
            throw new GroupException(MEMBER_IS_NOT_PARTICIPANT);
        }
    }

    private void validateMemberIsNotParticipant(Member member) {
        if (contains(member)) {
            throw new GroupException(MEMBER_IS_PARTICIPANT);
        }
    }

    private void validateParticipantsNotYetFull() {
        if (isFull()) {
            throw new GroupException(ALREADY_PARTICIPANTS_SIZE_FULL);
        }
    }

    private void validateCapacityIsOverParticipantsSize(Capacity capacity) {
        if (capacity.isSmallThan(getSize())) {
            throw new GroupException(CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE);
        }
    }

    private boolean contains(Member member) {
        return getParticipants().contains(member);
    }

    public boolean isNotEmpty() {
        return !participants.isEmpty();
    }

    public boolean isFull() {
        return capacity.isEqualOrOver(getSize());
    }

    public boolean isHost(Member member) {
        return host.isSameUserId(member);
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
