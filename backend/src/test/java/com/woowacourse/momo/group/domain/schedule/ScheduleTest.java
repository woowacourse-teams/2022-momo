package com.woowacourse.momo.group.domain.schedule;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.group.fixture.GroupFixture._10시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._12시_00분;
import static com.woowacourse.momo.group.fixture.GroupFixture._7월_1일;

import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ScheduleTest {

    @DisplayName("시작 시간은 종료 시간 이전이어야 한다")
    @ParameterizedTest(name = "시작 시간: {0}, 종료 시간: {1}")
    @MethodSource("provideForScheduleValidator")
    void validateStartIsBeforeEnd(LocalTime startTime, LocalTime endTime) {
        assertThatThrownBy(() -> new Schedule(_7월_1일, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 시간은 종료 시간 이전이어야 합니다.");
    }

    private static Stream<Arguments> provideForScheduleValidator() {
        return Stream.of(
                Arguments.of(_10시_00분, _10시_00분),
                Arguments.of(_12시_00분, _10시_00분)
        );
    }
}
