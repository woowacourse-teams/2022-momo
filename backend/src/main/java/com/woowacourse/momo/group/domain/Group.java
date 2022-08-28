package com.woowacourse.momo.group.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.group.domain.calendar.Calendar;
import com.woowacourse.momo.group.domain.calendar.Deadline;
import com.woowacourse.momo.group.domain.calendar.Duration;
import com.woowacourse.momo.group.domain.calendar.Schedule;
import com.woowacourse.momo.group.domain.calendar.Schedules;
import com.woowacourse.momo.group.domain.participant.Capacity;
import com.woowacourse.momo.group.domain.participant.Participants;
import com.woowacourse.momo.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private GroupName name;

    @ManyToOne
    @JoinColumn
    private Member host;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Calendar calendar;

    @Embedded
    private Participants participants;

    @Column(nullable = false)
    private String location;

    @Lob
    @Column(nullable = false)
    private String description;

    private boolean isEarlyClosed;


    public Group(GroupName name, Member host, Category category, Capacity capacity, Calendar calendar,
            String location, String description) {
        this.name = name;
        this.host = host;
        this.category = category;
        this.calendar = calendar;
        this.location = location;
        this.description = description;
        this.participants = new Participants(this, capacity);
    }

    public Group(GroupName name, Member host, Category category, Capacity capacity, Duration duration,
                 Deadline deadline, Schedules schedules, String location, String description) {
        this(name, host, category, capacity, new Calendar(deadline, duration, schedules), location, description);
    }

    public void update(GroupName name, Category category, Capacity capacity, Calendar calendar,
                       String location, String description) {
        validateGroupIsInitialState();
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;

        participants.update(capacity);
        this.calendar.update(calendar.getDeadline(), calendar.getDuration(), calendar.getSchedules());
    }

    public void participate(Member member) {
        participants.participate(this, member);
    }

    public void closeEarly() {
        validateGroupCanBeCloseEarly();
        isEarlyClosed = true;
    }

    public boolean isHost(Member member) {
        return host.equals(member);
    }

    public boolean isNotHost(Member member) {
        return !host.equals(member);
    }

    public boolean isEnd() {
        return isEarlyClosed || calendar.isDeadlineOver();
    }

    public boolean isFinishedRecruitment() {
        return isEarlyClosed || participants.isFull() || calendar.isDeadlineOver();
    }

    public void validateMemberCanLeave(Member member) {
        validate(() -> !participants.contains(member), ErrorCode.PARTICIPANT_LEAVE_NOT_PARTICIPANT);
        validate(calendar::isDeadlineOver, ErrorCode.PARTICIPANT_LEAVE_DEADLINE);
        validate(() -> isEarlyClosed, ErrorCode.PARTICIPANT_LEAVE_EARLY_CLOSED);
    }

    public void validateGroupIsInitialState() {
        validate(this::isFinishedRecruitment, ErrorCode.GROUP_ALREADY_FINISH);
        validate(participants::isExist, ErrorCode.GROUP_EXIST_PARTICIPANTS);
    }

    private void validateGroupCanBeCloseEarly() {
        validate(this::isFinishedRecruitment, ErrorCode.GROUP_ALREADY_FINISH);
    }

    private void validate(ExceptionPredicate exceptionPredicate, ErrorCode errorCode) {
        if (exceptionPredicate.run()) {
            throw new MomoException(errorCode);
        }
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
