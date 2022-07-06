package com.woowacourse.momo.domain.group;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DurationTest {

    @DisplayName("시작일은 종료일 이전 혹은 같을 수 있다")
    @ParameterizedTest
    @CsvSource(value = {"2020-05-08,2020-05-08", "2020-05-07,2020-05-08"})
    void construct(final String start, final String end) {
        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE);

        assertDoesNotThrow(() -> new Duration(startDate, endDate));
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        LocalDate startDate = LocalDate.parse("2020-05-08", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endDate = LocalDate.parse("2020-05-07", DateTimeFormatter.ISO_LOCAL_DATE);

        assertThatThrownBy(() -> new Duration(startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일 이후가 될 수 없습니다.");
    }
}
