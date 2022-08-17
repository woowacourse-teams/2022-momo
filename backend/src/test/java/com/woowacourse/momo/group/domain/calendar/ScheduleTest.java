package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.TimeFixture._12시_00분;

import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class ScheduleTest {

    private static Stream<Arguments> provideForScheduleValidator() {
        return Stream.of(
                Arguments.of(_10시_00분.getInstance(), _10시_00분.getInstance()),
                Arguments.of(_12시_00분.getInstance(), _10시_00분.getInstance())
        );
    }

    @DisplayName("시작 시간은 종료 시간 이전이어야 한다")
    @ParameterizedTest(name = "시작 시간: {0}, 종료 시간: {1}")
    @MethodSource("provideForScheduleValidator")
    void validateStartIsBeforeEnd(LocalTime startTime, LocalTime endTime) {
        assertThatThrownBy(() -> new Schedule(이틀후.getInstance(), startTime, endTime))
                .isInstanceOf(MomoException.class)
                .hasMessage("일정의 시작 시간은 종료 시간 이전이어야 합니다.");
    }
}
