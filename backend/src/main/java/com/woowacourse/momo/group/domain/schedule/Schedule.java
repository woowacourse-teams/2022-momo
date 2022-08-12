package com.woowacourse.momo.group.domain.schedule;

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

    private void validateStartIsBeforeEnd(LocalTime startTime, LocalTime endTime) {
        if (!endTime.isAfter(startTime)) {
            throw new MomoException(ErrorCode.GROUP_SCHEDULE_START_AFTER_END);
        }
    }

    public boolean checkInRange(LocalDate startDate, LocalDate endDate) {
        return (date.isAfter(startDate) | date.isEqual(startDate))
                && (date.isBefore(endDate) | date.isEqual(endDate));
    }
}
