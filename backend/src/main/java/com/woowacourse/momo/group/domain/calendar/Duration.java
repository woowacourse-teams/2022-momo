package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DURATION_MUST_BE_SET_FROM_NOW_ON;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DURATION_START_DATE_MUST_BE_BEFORE_END_DATE;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.exception.GroupException;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        return Objects.equals(startDate, duration.startDate) && Objects.equals(endDate,
                duration.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "Duration{" + startDate + "~" + endDate + '}';
    }
}
