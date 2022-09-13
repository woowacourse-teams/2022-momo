package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupErrorCode.DURATION_MUST_BE_SET_FROM_NOW_ON;
import static com.woowacourse.momo.group.exception.GroupErrorCode.DURATION_START_DATE_MUST_BE_BEFORE_END_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.group.exception.GroupException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
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
            throw new GroupException(DURATION_START_DATE_MUST_BE_BEFORE_END_DATE);
        }
    }

    private void validateDatesAreNotPast(LocalDate startDate, LocalDate endDate) {
        if (hasAnyDatesBeforeThanNow(startDate, endDate)) {
            throw new GroupException(DURATION_MUST_BE_SET_FROM_NOW_ON);
        }
    }

    private boolean hasAnyDatesBeforeThanNow(LocalDate... dates) {
        return Stream.of(dates)
                .anyMatch(this::isBeforeThanNow);
    }

    private boolean isBeforeThanNow(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
