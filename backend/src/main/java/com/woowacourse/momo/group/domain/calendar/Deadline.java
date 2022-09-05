package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DEADLINE_MUST_BE_SET_FROM_NOW_ON;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.exception.GroupException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Deadline {

    @Column(name = "deadline", nullable = false)
    private LocalDateTime value;

    public Deadline(LocalDateTime value) {
        validateDeadlineIsAfterNow(value);
        this.value = value;
    }

    public boolean isPast() {
        return value.isBefore(LocalDateTime.now());
    }

    public boolean isAfterThan(LocalDate date) {
        return value.toLocalDate()
                .isAfter(date);
    }

    private void validateDeadlineIsAfterNow(LocalDateTime value) {
        if (isBeforeThanNow(value)) {
            throw new GroupException(DEADLINE_MUST_BE_SET_FROM_NOW_ON);
        }
    }

    private boolean isBeforeThanNow(LocalDateTime value) {
        return value.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deadline deadline = (Deadline) o;
        return Objects.equals(value, deadline.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Deadline{" + value + '}';
    }
}
