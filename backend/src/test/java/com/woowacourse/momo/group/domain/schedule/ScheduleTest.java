package com.woowacourse.momo.group.domain.schedule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ScheduleTest {

    @DisplayName("날짜는 yyyy-MM-dd 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"2022", "2022.01.01"})
    void validateDateFormat(String date) {
        assertThatThrownBy(() -> Schedule.of(date, "11:11", "13:11"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜는 yyyy-MM-dd 형식이어야 합니다.");
    }

    @DisplayName("시작 시간은 hh:mm 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"11", "11-11", "14:90"})
    void validateStartTimeFormat(String time) {
        assertThatThrownBy(() -> Schedule.of("2022-01-01", time, "11:11"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 hh:mm 형식이어야 합니다.");
    }

    @DisplayName("종료 시간은 hh:mm 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {"2022", "2022.01.01"})
    void validateEndTimeFormat(String time) {
        assertThatThrownBy(() -> Schedule.of("2022-01-01", "11:11", time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 hh:mm 형식이어야 합니다.");
    }
}
