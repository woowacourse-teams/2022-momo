package com.woowacourse.momo.group.domain.duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.woowacourse.momo.group.exception.InvalidDurationException;

class DurationTest {

    @DisplayName("시작일은 종료일 이전 혹은 같을 수 있다")
    @ParameterizedTest
    @CsvSource(value = {"2020-05-08,2020-05-08", "2020-05-07,2020-05-08"})
    void construct(final String start, final String end) {
        assertDoesNotThrow(() -> Duration.of(start, end));
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> Duration.of("2020-05-08", "2020-05-07"))
                .isInstanceOf(InvalidDurationException.class);
    }
}
