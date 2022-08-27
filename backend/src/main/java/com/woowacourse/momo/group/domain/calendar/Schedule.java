package com.woowacourse.momo.group.domain.calendar;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public boolean isOutOfDuration(Duration duration) {
        return duration.isNotContainable(date);
    }

    private void validateStartIsBeforeEnd(LocalTime startTime, LocalTime endTime) {
        if (isStartNotBeforeEnd(startTime, endTime)) {
            throw new MomoException(ErrorCode.GROUP_SCHEDULE_START_AFTER_END);
        }
    }

    private boolean isStartNotBeforeEnd(LocalTime startTime, LocalTime endTime) {
        return !startTime.isBefore(endTime);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", " + date +
                " " + startTime + "~" + endTime +
                '}';
    }
}
