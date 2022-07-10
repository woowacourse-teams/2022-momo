package com.woowacourse.momo.domain.group;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DurationTest {

    @DisplayName("시작일은 종료일 이전 혹은 같을 수 있다")
    @ParameterizedTest
    @CsvSource(value = {"2020-05-08,2020-05-08", "2020-05-07,2020-05-08"})
    void construct(final String startDate, final String endDate) {
        assertDoesNotThrow(() -> Duration.of(startDate, endDate));
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> Duration.of("2020-05-08", "2020-05-07"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일 이후가 될 수 없습니다.");
    }
}
