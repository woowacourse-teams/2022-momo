package com.woowacourse.momo.group.domain.participant;

import static com.woowacourse.momo.group.exception.GroupExceptionMessage.CAPACITY_ALREADY_FULL;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_IS_ALREADY_PARTICIPANT;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_IS_NOT_PARTICIPANT;

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

    @ManyToOne
    @JoinColumn
    private Member host;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private final List<Participant> participants = new ArrayList<>();

    @Embedded
    private Capacity capacity;

    public Participants(Member host, Capacity capacity) {
        this.host = host;
        this.capacity = capacity;
    }

    public void participate(Group group, Member member) {
        validateMemberIsNotHost(member);
        validateMemberIsNotParticipant(member);
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
                .orElseThrow(() -> new GroupException(MEMBER_IS_NOT_PARTICIPANT));
    }

    private void validateMemberIsNotHost(Member member) {
        if (host.equals(member)) {
            throw new GroupException(HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP);
        }
    }

    private void validateMemberIsNotParticipant(Member member) {
        if (contains(member)) {
            throw new GroupException(MEMBER_IS_ALREADY_PARTICIPANT);
        }
    }

    private boolean contains(Member member) {
        return getParticipants().contains(member);
    }

    private void validateParticipantsNotYetFull() {
        if (isFull()) {
            throw new GroupException(CAPACITY_ALREADY_FULL);
        }
    }

    public void updateCapacity(Capacity capacity) {
        validateCapacityIsOverParticipantsSize(capacity);
        this.capacity = capacity;
    }

    private void validateCapacityIsOverParticipantsSize(Capacity capacity) {
        if (capacity.isUnder(getSize())) {
            throw new GroupException(CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE);
        }
    }

    public boolean isNotEmpty() {
        return !participants.isEmpty();
    }

    public boolean isFull() {
        return capacity.isSame(getSize());
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
