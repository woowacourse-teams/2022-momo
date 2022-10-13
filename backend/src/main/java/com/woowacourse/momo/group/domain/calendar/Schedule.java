package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupErrorCode.SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.domain.Group;
import com.woowacourse.momo.group.exception.GroupException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public Schedule(LocalDate date, LocalTime startTime, LocalTime endTime) {
        validateStartIsBeforeEnd(startTime, endTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void assignGroup(Group group) {
        this.group = group;
    }

    public boolean isOutOfDuration(Duration duration) {
        return duration.isNotContainable(date);
    }

    private void validateStartIsBeforeEnd(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new GroupException(SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME);
        }
    }

    public boolean equalsDateTime(Schedule schedule) {
        return date.equals(schedule.date) &&
                startTime.equals(schedule.startTime) &&
                endTime.equals(schedule.endTime);
    }
}
