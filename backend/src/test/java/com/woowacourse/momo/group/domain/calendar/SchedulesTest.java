package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SchedulesTest {

    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    @DisplayName("일정들이 기간 내에 속해있는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideHasAnyScheduleOutOfDurationArguments")
    void hasAnyScheduleOutOfDuration(LocalDate date, boolean expected) {
        Schedule schedule = new Schedule(date, LocalTime.now(), LocalTime.now().plusHours(1));
        Schedules schedules = new Schedules(List.of(schedule));
        Duration duration = new Duration(NOW, TOMORROW);

        assertThat(schedules.hasAnyScheduleOutOfDuration(duration)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideHasAnyScheduleOutOfDurationArguments() {
        return Stream.of(
                Arguments.of(YESTERDAY, Boolean.TRUE),
                Arguments.of(NOW, Boolean.FALSE)
        );
    }
}
