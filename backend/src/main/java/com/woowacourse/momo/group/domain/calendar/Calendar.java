package com.woowacourse.momo.group.domain.calendar;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Calendar {

    @Embedded
    private Schedules schedules;

    @Embedded
    private Duration duration;

    @Embedded
    private Deadline deadline;

    public Calendar(Deadline deadline, Duration duration, Schedules schedules) {
        validateCalendarIsValid(schedules, duration, deadline);
        this.schedules = schedules;
        this.duration = duration;
        this.deadline = deadline;
    }

    public Calendar(Deadline deadline, Duration duration, List<Schedule> schedules) {
        this(deadline, duration, new Schedules(schedules));
    }

    public void update(Deadline deadline, Duration duration, Schedules schedules) {
        validateCalendarIsValid(schedules, duration, deadline);
        this.schedules.change(schedules);
        this.duration = duration;
        this.deadline = deadline;
    }

    public boolean isDeadlineOver() {
        return deadline.isPast();
    }

    private void validateCalendarIsValid(Schedules schedules, Duration duration, Deadline deadline) {
        validateDeadlineIsNotAfterDurationStart(duration, deadline);
        validateSchedulesAreInDuration(schedules, duration);
    }

    private void validateDeadlineIsNotAfterDurationStart(Duration duration, Deadline deadline) {
        if (duration.isStartBeforeDeadline(deadline)) {
            throw new MomoException(ErrorCode.GROUP_DURATION_NOT_AFTER_DEADLINE);
        }
    }

    private void validateSchedulesAreInDuration(Schedules schedules, Duration duration) {
        if (schedules.hasAnyScheduleOutOfDuration(duration)) {
            throw new MomoException(ErrorCode.GROUP_SCHEDULE_NOT_RANGE_DURATION);
        }
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "schedules=" + schedules +
                ", duration=" + duration +
                ", deadline=" + deadline +
                '}';
    }
}
