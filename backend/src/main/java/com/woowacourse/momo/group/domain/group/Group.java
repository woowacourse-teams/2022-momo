package com.woowacourse.momo.group.domain.group;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.AUTH_DELETE_NO_HOST;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_ALREADY_FINISH;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_EXIST_PARTICIPANTS;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_DEADLINE;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_EARLY_CLOSED;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_HOST;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.PARTICIPANT_LEAVE_NOT_PARTICIPANT;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.participant.domain.Participants;

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

    public Group(GroupName name, Member host, Category category, Capacity capacity, Duration duration,
                 Deadline deadline, List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.host = host;
        this.category = category;
        this.calendar = new Calendar(schedules, duration, deadline);
        this.location = location;
        this.description = description;

        this.participants = new Participants(this, capacity);
    }

    public void update(GroupName name, Member host, Category category, Capacity capacity, Duration duration, Deadline deadline,
                       List<Schedule> schedules, String location, String description) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;

        participants.update(capacity);
        calendar.update(schedules, duration, deadline);
        validateGroupIsInitialState(host);
    }

    public void participate(Member member) {
        participants.participate(this, member);
    }

    public void closeEarly(Member member) {
        validateGroupCanBeCloseEarly(member);
        isEarlyClosed = true;
    }

    public boolean isHost(Member host) {
        return this.host.equals(host);
    }

    public boolean isEnd() {
        return isEarlyClosed || calendar.isDeadlineOver();
    }

    public boolean isFinishedRecruitment() {
        return isEarlyClosed || participants.isFull() || calendar.isDeadlineOver();
    }

    public void validateMemberCanLeave(Member member) {
        validateTemplate((() -> isHost(member)), PARTICIPANT_LEAVE_HOST);
        validateTemplate((() -> !participants.isParticipant(member)), PARTICIPANT_LEAVE_NOT_PARTICIPANT);
        validateTemplate((() -> calendar.isDeadlineOver()), PARTICIPANT_LEAVE_DEADLINE);
        validateTemplate((() -> isEarlyClosed), PARTICIPANT_LEAVE_EARLY_CLOSED);
    }

    public void validateGroupIsInitialState(Member member) {
        validateTemplate((() -> !isHost(member)), AUTH_DELETE_NO_HOST);
        validateTemplate((this::isFinishedRecruitment), GROUP_ALREADY_FINISH);
        validateTemplate((() -> participants.isExist()), GROUP_EXIST_PARTICIPANTS);
    }

    private void validateGroupCanBeCloseEarly(Member member) {
        validateTemplate((() -> !isHost(member)), AUTH_DELETE_NO_HOST);
        validateTemplate((this::isFinishedRecruitment), GROUP_ALREADY_FINISH);
    }

    private void validateTemplate(ExceptionPredicate exceptionPredicate, ErrorCode errorCode) {
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

    public static class Builder {

        private GroupName name;
        private Member host;
        private Category category;
        private Capacity capacity;
        private Duration duration;
        private Deadline deadline;
        private List<Schedule> schedules;
        private String location;
        private String description;

        public Builder() {
        }

        public Builder name(GroupName name) {
            this.name = name;
            return this;
        }

        public Builder host(Member host) {
            this.host = host;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder categoryId(long categoryId) {
            this.category = Category.from(categoryId);
            return this;
        }

        public Builder capacity(Capacity capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder deadline(Deadline deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder schedules(List<Schedule> schedules) {
            this.schedules = schedules;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Group build() {
            validateNonNull();
            return new Group(name, host, category, capacity, duration, deadline, schedules, location,
                    description);
        }

        private void validateNonNull() {
            Objects.requireNonNull(name);
            Objects.requireNonNull(host);
            Objects.requireNonNull(category);
            Objects.requireNonNull(duration);
            Objects.requireNonNull(deadline);
            Objects.requireNonNull(schedules);
            Objects.requireNonNull(location);
            Objects.requireNonNull(description);
        }
    }
}
