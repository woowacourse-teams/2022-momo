package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupErrorCode.DURATION_MUST_BE_SET_BEFORE_DEADLINE;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.exception.GroupException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Calendar {

    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST)
    private final List<Schedule> schedules = new ArrayList<>();

    @Embedded
    private Duration duration;

    @Embedded
    private Deadline deadline;

    public Calendar(Deadline deadline, Duration duration) {
        validateDeadlineIsNotAfterDurationStart(duration, deadline);
        this.duration = duration;
        this.deadline = deadline;
    }

    public void update(Deadline deadline, Duration duration) {
        validateDeadlineIsNotAfterDurationStart(duration, deadline);
        this.duration = duration;
        this.deadline = deadline;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public boolean isDeadlineOver() {
        return deadline.isPast();
    }

    private void validateDeadlineIsNotAfterDurationStart(Duration duration, Deadline deadline) {
        if (duration.isStartBeforeDeadline(deadline)) {
            throw new GroupException(DURATION_MUST_BE_SET_BEFORE_DEADLINE);
        }
    }
}
