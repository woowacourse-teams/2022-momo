package com.woowacourse.momo.group.domain.duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Duration {

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private Duration(LocalDate startDate, LocalDate endDate) {
        validateEndIsNotBeforeStart(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Duration of(LocalDate startDate, LocalDate endDate) {
        return new Duration(startDate, endDate);
    }

    public static Duration of(String startDate, String endDate) {
        return Duration.of(
                LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    private void validateEndIsNotBeforeStart(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이후가 될 수 없습니다.");
        }
    }
}
