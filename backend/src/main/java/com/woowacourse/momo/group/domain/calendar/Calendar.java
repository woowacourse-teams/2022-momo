package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_DURATION_NOT_AFTER_DEADLINE;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_SCHEDULE_NOT_RANGE_DURATION;

import java.util.List;

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
        this.schedules = schedules;
        this.duration = duration;
        this.deadline = deadline;
        validateIsBeforeStartDuration();
        validateSchedulesAreInDuration();
    }

    public void update(Schedules schedules, Duration duration, Deadline deadline) {
        this.schedules.change(schedules);
        this.duration = duration;
        this.deadline = deadline;
        validateIsBeforeStartDuration();
        validateSchedulesAreInDuration();
        // TODO: deadline 검증 추가 필요
    }

    public boolean isDeadlineOver() {
        return deadline.isOver();
    }

    private void validateIsBeforeStartDuration() {
        if (duration.isAfterStartDate(deadline.getValue())) {
            throw new MomoException(GROUP_DURATION_NOT_AFTER_DEADLINE);
        }
    }

    private void validateSchedulesAreInDuration() {
        if (schedules.isExistAnyScheduleOutOfDuration(duration)) {
            throw new MomoException(GROUP_SCHEDULE_NOT_RANGE_DURATION);
        }
    }
}
