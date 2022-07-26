package com.woowacourse.momo.group.domain.duration;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.group.exception.InvalidDurationException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Duration {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public Duration(LocalDate startDate, LocalDate endDate) {
        validateEndIsNotBeforeStart(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateEndIsNotBeforeStart(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new InvalidDurationException();
        }
    }
}
