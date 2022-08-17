package com.woowacourse.momo.group.domain.duration;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        this.startDate = startDate;
        this.endDate = endDate;
        validatePastDate();
        validateEndIsNotBeforeStart();
    }

    public boolean isAfterStartDate(LocalDateTime date) {
        return startDate.isBefore(LocalDate.from(date));
    }

    private void validatePastDate() {
        if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())) {
            throw new MomoException(ErrorCode.GROUP_DURATION_NOT_PAST);
        }
    }

    private void validateEndIsNotBeforeStart() {
        if (endDate.isBefore(startDate)) {
            throw new MomoException(ErrorCode.GROUP_DURATION_START_AFTER_END);
        }
    }
}
