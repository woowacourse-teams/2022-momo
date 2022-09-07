package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupErrorCode.DURATION_MUST_BE_SET_BEFORE_DEADLINE;
import static com.woowacourse.momo.group.exception.GroupErrorCode.SCHEDULE_MUST_BE_INCLUDED_IN_DURATION;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.group.exception.GroupException;

@ToString
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
            throw new GroupException(DURATION_MUST_BE_SET_BEFORE_DEADLINE);
        }
    }

    private void validateSchedulesAreInDuration(Schedules schedules, Duration duration) {
        if (schedules.hasAnyScheduleOutOfDuration(duration)) {
            throw new GroupException(SCHEDULE_MUST_BE_INCLUDED_IN_DURATION);
        }
    }
}
