package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.GROUP_DURATION_NOT_AFTER_DEADLINE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Calendar {

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "group_id")
    private List<Schedule> schedules = new ArrayList<>();

    @Embedded
    private Duration duration;

    @Embedded
    private Deadline deadline;

    public Calendar(List<Schedule> schedules, Duration duration, Deadline deadline) {
        this.schedules.addAll(schedules);
        this.duration = duration;
        this.deadline = deadline;
        validateIsBeforeStartDuration();
    }

    public void update(List<Schedule> schedules, Duration duration, LocalDateTime deadline) {
        this.schedules.clear();
        this.schedules.addAll(schedules);
        this.duration = duration;
        this.deadline = new Deadline(deadline);
        validateIsBeforeStartDuration();
    }

    public boolean isDeadlineOver() {
        return deadline.isOver();
    }

    private void validateIsBeforeStartDuration() {
        if (duration.isAfterStartDate(deadline.getValue())) {
            throw new MomoException(GROUP_DURATION_NOT_AFTER_DEADLINE);
        }
    }
}
