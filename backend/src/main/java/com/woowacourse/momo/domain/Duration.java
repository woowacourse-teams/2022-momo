package com.woowacourse.momo.domain;

import java.time.LocalDate;

public class Duration {

    private final LocalDate startDate;
    private final LocalDate endDate;

    public Duration(final LocalDate startDate, final LocalDate endDate) {
        validateEndIsNotBeforeStart(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateEndIsNotBeforeStart(final LocalDate startDate, final LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이후가 될 수 없습니다.");
        }
    }
}
