package com.woowacourse.momo.group.domain;

import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_ALREADY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_ALREADY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_PARTICIPANT_EXIST;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_PARTICIPANT_EXIST;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.domain.participant.Participants;
import com.woowacourse.momo.group.exception.GroupException;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Participants participants;

    @Embedded
    private Calendar calendar;

    @Embedded
    private GroupName name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    private boolean closedEarly;

    public Group(Member host, Capacity capacity, Calendar calendar, GroupName name, Category category,
                 String location, String description) {
        this.participants = new Participants(host, capacity);
        this.calendar = calendar;
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
    }

    public void update(Capacity capacity, Calendar calendar, GroupName name, Category category,
                       String location, String description) {
        validateGroupIsUpdatable();
        this.participants.updateCapacity(capacity);
        this.calendar.update(calendar.getDeadline(), calendar.getDuration(), calendar.getSchedules());
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
    }

    public void closeEarly() {
        validateGroupCanBeCloseEarly();
        closedEarly = true;
    }

    private void validateGroupIsUpdatable() {
        if (closedEarly) {
            throw new GroupException(GROUP_UPDATE_FAILED_BY_CLOSED_EARLY);
        }
        if (calendar.isDeadlineOver()) {
            throw new GroupException(GROUP_UPDATE_FAILED_BY_DEADLINE_OVER);
        }
        if (participants.isNotEmpty()) {
            throw new GroupException(GROUP_UPDATE_FAILED_BY_PARTICIPANT_EXIST);
        }
    }

    public void validateGroupIsDeletable() {
        if (closedEarly) {
            throw new GroupException(GROUP_DELETE_FAILED_BY_CLOSED_EARLY);
        }
        if (calendar.isDeadlineOver()) {
            throw new GroupException(GROUP_DELETE_FAILED_BY_DEADLINE_OVER);
        }
        if (participants.isNotEmpty()) {
            throw new GroupException(GROUP_DELETE_FAILED_BY_PARTICIPANT_EXIST);
        }
    }

    private void validateGroupCanBeCloseEarly() {
        if (closedEarly) {
            throw new GroupException(GROUP_ALREADY_CLOSED_EARLY);
        }
        if (calendar.isDeadlineOver()) {
            throw new GroupException(GROUP_ALREADY_DEADLINE_OVER);
        }
    }

    public void participate(Member member) {
        validateGroupIsProceeding();
        participants.participate(this, member);
    }

    public void leave(Member member) {
        validateGroupIsProceeding();
        participants.leave(member);
    }

    private void validateGroupIsProceeding() {
        if (closedEarly) {
            throw new GroupException(GROUP_ALREADY_CLOSED_EARLY);
        }
        if (calendar.isDeadlineOver()) {
            throw new GroupException(GROUP_ALREADY_DEADLINE_OVER);
        }
    }

    public boolean isHost(Member member) {
        return participants.isHost(member);
    }

    public boolean isNotHost(Member member) {
        return !isHost(member);
    }

    public boolean isFinishedRecruitment() {
        return closedEarly || calendar.isDeadlineOver() || participants.isFull();
    }

    public Member getHost() {
        return participants.getHost();
    }

    public String getName() {
        return name.getValue();
    }

    public int getCapacity() {
        return participants.getCapacity().getValue();
    }

    public List<Schedule> getSchedules() {
        return calendar.getSchedules().getValue();
    }

    public Duration getDuration() {
        return calendar.getDuration();
    }

    public LocalDateTime getDeadline() {
        return calendar.getDeadline().getValue();
    }

    public List<Member> getParticipants() {
        return participants.getParticipants();
    }
}
