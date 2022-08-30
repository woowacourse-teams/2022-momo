package com.woowacourse.momo.group.domain.calendar;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Duration {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public Duration(LocalDate startDate, LocalDate endDate) {
        validateStartIsBeforeEnd(startDate, endDate);
        validateDatesAreNotPast(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isNotContainable(LocalDate date) {
        return date.isBefore(startDate) || date.isAfter(endDate);
    }

    public boolean isStartBeforeDeadline(Deadline deadline) {
        return deadline.isAfterThan(startDate);
    }

    private void validateStartIsBeforeEnd(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new MomoException(ErrorCode.GROUP_DURATION_START_AFTER_END);
        }
    }

    private void validateDatesAreNotPast(LocalDate startDate, LocalDate endDate) {
        if (hasAnyDatesBeforeThanNow(startDate, endDate)) {
            throw new MomoException(ErrorCode.GROUP_DURATION_NOT_PAST);
        }
    }

    private boolean hasAnyDatesBeforeThanNow(LocalDate... dates) {
        return Stream.of(dates)
                .anyMatch(this::isBeforeThanNow);
    }

    private boolean isBeforeThanNow(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Duration{" + startDate + "~" + endDate + '}';
    }
}
