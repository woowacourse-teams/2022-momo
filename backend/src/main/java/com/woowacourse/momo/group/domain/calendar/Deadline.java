package com.woowacourse.momo.group.domain.calendar;

import static com.woowacourse.momo.group.exception.GroupErrorCode.DEADLINE_MUST_BE_SET_FROM_NOW_ON;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
