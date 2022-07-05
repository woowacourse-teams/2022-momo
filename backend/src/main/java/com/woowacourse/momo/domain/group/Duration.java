package com.woowacourse.momo.domain.group;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@NoArgsConstructor
@Embeddable
public class Duration {

    private LocalDate startDate;
    private LocalDate endDate;

    public Duration(LocalDate startDate, LocalDate endDate) {
        validateEndIsNotBeforeStart(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateEndIsNotBeforeStart(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이후가 될 수 없습니다.");
        }
    }
}
