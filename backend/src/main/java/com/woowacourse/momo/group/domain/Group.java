package com.woowacourse.momo.group.domain;

import static com.woowacourse.momo.group.exception.GroupErrorCode.ALREADY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupErrorCode.ALREADY_DEADLINE_OVER;

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
import javax.persistence.Index;
import javax.persistence.Table;

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
@Table(indexes = @Index(name = "group_deadline", columnList = "deadline"))
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

    @Embedded
    private Location location;

    @Embedded
    private Description description;

    private boolean closedEarly;

    public Group(Member host, Capacity capacity, Calendar calendar, GroupName name, Category category,
                 Location location, Description description) {
        this.participants = new Participants(host, capacity);
        this.calendar = calendar;
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
    }

    public void update(Capacity capacity, Calendar calendar, GroupName name, Category category,
                       Location location, Description description) {
        validateGroupIsProceeding();
        this.participants.updateCapacity(capacity);
        this.calendar.update(calendar.getDeadline(), calendar.getDuration());
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
    }

    public void closeEarly() {
        validateGroupIsProceeding();
        closedEarly = true;
    }

    public void participate(Member member) {
        validateGroupIsProceeding();
        participants.participate(this, member);
    }

    public void remove(Member participant) {
        validateGroupIsProceeding();
        participants.remove(participant);
    }

    public void validateGroupIsProceeding() {
        validateDeadlineNotOver();
        validateGroupIsNotClosedEarly();
    }

    private void validateGroupIsNotClosedEarly() {
        if (closedEarly) {
            throw new GroupException(ALREADY_CLOSED_EARLY);
        }
    }

    private void validateDeadlineNotOver() {
        if (calendar.isDeadlineOver()) {
            throw new GroupException(ALREADY_DEADLINE_OVER);
        }
    }

    public void addSchedule(Schedule schedule) {
        schedule.assignGroup(this);
        calendar.addSchedule(schedule);
    }

    public boolean isHost(Member member) {
        return participants.isHost(member);
    }

    public boolean isNotHost(Member member) {
        return !isHost(member);
    }

    public boolean isFinishedRecruitment() {
        return closedEarly || calendar.isDeadlineOver();
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
        return calendar.getSchedules();
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

    public Description getDescription() {
        return description;
    }
}
