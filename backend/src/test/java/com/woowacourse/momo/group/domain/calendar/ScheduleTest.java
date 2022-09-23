package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.calendar.DurationFixture.내일_하루동안;
import static com.woowacourse.momo.fixture.calendar.DurationFixture.일주일후_하루동안;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.내일_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.ScheduleFixture.이틀후_10시부터_12시까지;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.내일;
import static com.woowacourse.momo.fixture.calendar.datetime.DateFixture.이틀후;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._10시_00분;
import static com.woowacourse.momo.fixture.calendar.datetime.TimeFixture._12시_00분;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.woowacourse.momo.group.exception.GroupException;

class ScheduleTest {

    @DisplayName("정상적으로 생성한다")
    @Test
    void construct() {
        LocalDate date = 내일.toDate();
        LocalTime startTime = _10시_00분.toTime();
        LocalTime endTime = _12시_00분.toTime();

        Schedule schedule = new Schedule(date, startTime, endTime);

        assertAll(
                () -> assertThat(schedule.getDate()).isEqualTo(date),
                () -> assertThat(schedule.getStartTime()).isEqualTo(startTime),
                () -> assertThat(schedule.getEndTime()).isEqualTo(endTime)
        );
    }

    @DisplayName("시작 시간은 종료 시간 이전이어야 한다")
    @ParameterizedTest(name = "{0}, {1} ~ {2}")
    @MethodSource("provideForScheduleValidator")
    void validateStartIsBeforeEnd(LocalDate date, LocalTime startTime, LocalTime endTime) {
        assertThatThrownBy(() -> new Schedule(date, startTime, endTime))
                .isInstanceOf(GroupException.class)
                .hasMessage("일정의 시작 시간은 종료 시간 이전이어야 합니다.");
    }

    private static Stream<Arguments> provideForScheduleValidator() {
        LocalDate yesterday = 이틀후.toDate();
        return Stream.of(
                Arguments.of(yesterday, _10시_00분.toTime(), _10시_00분.toTime()),
                Arguments.of(yesterday, _12시_00분.toTime(), _10시_00분.toTime())
        );
    }

    @DisplayName("기간에 포함될 수 없음을 확인한다")
    @ParameterizedTest
    @MethodSource("provideForIsOutOfDuration")
    void isOutOfDuration(Duration duration, Schedule schedule, boolean expected) {
        assertThat(schedule.isOutOfDuration(duration)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsOutOfDuration() {
        return Stream.of(
                Arguments.of(내일_하루동안.toDuration(), 내일_10시부터_12시까지.toSchedule(), false),
                Arguments.of(내일_하루동안.toDuration(), 이틀후_10시부터_12시까지.toSchedule(), true),
                Arguments.of(일주일후_하루동안.toDuration(), 이틀후_10시부터_12시까지.toSchedule(), true)
        );
    }
}
