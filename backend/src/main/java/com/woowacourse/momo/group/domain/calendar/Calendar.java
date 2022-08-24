package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_DURATION_NOT_AFTER_DEADLINE;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_SCHEDULE_NOT_RANGE_DURATION;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Calendar(Schedules schedules, Duration duration, Deadline deadline) {
        validateIsBeforeStartDuration(duration, deadline);
        validateSchedulesAreInDuration(schedules, duration);
        this.schedules = schedules;
        this.duration = duration;
        this.deadline = deadline;
    }

    public void update(Schedules schedules, Duration duration, Deadline deadline) {
        validateIsBeforeStartDuration(duration, deadline);
        validateSchedulesAreInDuration(schedules, duration);
        this.schedules.change(schedules);
        this.duration = duration;
        this.deadline = deadline;
        // TODO: deadline 검증 추가 필요
    }

    public boolean isDeadlineOver() {
        return deadline.isPast();
    }

    private void validateIsBeforeStartDuration(Duration duration, Deadline deadline) {
        if (duration.isAfterStartDate(deadline.getValue())) {
            throw new MomoException(GROUP_DURATION_NOT_AFTER_DEADLINE);
        }
    }

    private void validateSchedulesAreInDuration(Schedules schedules, Duration duration) {
        if (schedules.hasAnyScheduleOutOfDuration(duration)) {
            throw new MomoException(GROUP_SCHEDULE_NOT_RANGE_DURATION);
        }
    }
}
