package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후_하루동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.이틀후부터_5일동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.내일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.일주일후_10시부터_12시까지;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.fixture.calendar.ScheduleFixture;

class SchedulesTest {

    @DisplayName("일정을 생성한다")
    @Test
    void construct() {
        Schedules actual = ScheduleFixture.toSchedules(내일_10시부터_12시까지, 이틀후_10시부터_12시까지);
        Schedules expected = ScheduleFixture.toSchedules(내일_10시부터_12시까지, 이틀후_10시부터_12시까지);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("일정을 수정한다")
    @Test
    void change() {
        Schedules schedules = ScheduleFixture.toSchedules(내일_10시부터_12시까지, 이틀후_10시부터_12시까지);
        Schedules expected = ScheduleFixture.toSchedules(이틀후_10시부터_12시까지, 일주일후_10시부터_12시까지);

        schedules.change(expected);

        assertThat(schedules.getValue()).usingRecursiveComparison()
                .isEqualTo(expected.getValue());
    }

    @DisplayName("기간을 벗어난 일정이 존재하는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideHasAnyScheduleOutOfDurationArguments")
    void hasAnyScheduleOutOfDuration(Schedules schedules, Duration duration, boolean expected) {
        assertThat(schedules.hasAnyScheduleOutOfDuration(duration)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideHasAnyScheduleOutOfDurationArguments() {
        return Stream.of(
                Arguments.of(ScheduleFixture.toSchedules(일주일후_10시부터_12시까지),
                        일주일후_하루동안.toDuration(), false),
                Arguments.of(ScheduleFixture.toSchedules(이틀후_10시부터_12시까지, 일주일후_10시부터_12시까지),
                        이틀후부터_5일동안.toDuration(), false),
                Arguments.of(ScheduleFixture.toSchedules(이틀후_10시부터_12시까지, 일주일후_10시부터_12시까지),
                        이틀후_하루동안.toDuration(), true)
        );
    }
}
