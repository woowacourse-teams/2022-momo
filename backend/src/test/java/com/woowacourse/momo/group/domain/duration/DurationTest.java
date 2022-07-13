package com.woowacourse.momo.group.domain.duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.exception.InvalidDurationException;

class DurationTest {

    @DisplayName("시작 날짜는 yyyy-MM-dd 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"2022", "2022.01.01"})
    void validateDateFormatStart(String start) {
        assertThatThrownBy(() -> Duration.of(start, "2022-05-08"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 yyyy-MM-dd 형식이어야 합니다.");
    }

    @DisplayName("종료 날짜는 yyyy-MM-dd 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"2022", "2022.01.01"})
    void validateDateFormatEnd(String end) {
        assertThatThrownBy(() -> Duration.of("2022-05-08", end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 yyyy-MM-dd 형식이어야 합니다.");
    }

    @DisplayName("시작일은 종료일 이전 혹은 같을 수 있다")
    @ParameterizedTest
    @CsvSource(value = {"2020-05-08,2020-05-08", "2020-05-07,2020-05-08"})
    void construct(String start, String end) {
        assertDoesNotThrow(() -> Duration.of(start, end));
    }

    @DisplayName("시작일은 종료일 이후가 될 수 없다")
    @Test
    void validateEndIsNotBeforeStart() {
        assertThatThrownBy(() -> Duration.of("2020-05-08", "2020-05-07"))
                .isInstanceOf(InvalidDurationException.class);
    }
}
