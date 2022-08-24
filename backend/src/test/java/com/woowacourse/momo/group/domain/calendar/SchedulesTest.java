package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SchedulesTest {

    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);

    @DisplayName("일정들이 기간 내에 속해있지 않을 경우 True 를 반환한다")
    @Test
    void isExistAnyScheduleOutOfDurationTrue() {
        Schedule schedule = new Schedule(YESTERDAY, LocalTime.now(), LocalTime.now().plusHours(1));
        Schedules schedules = new Schedules(List.of(schedule));
        Duration duration = new Duration(NOW, TOMORROW);

        assertThat(schedules.hasAnyScheduleOutOfDuration(duration)).isTrue();
    }

    @DisplayName("일정들이 기간 내에 속해있을 경우 False 를 반환한다")
    @Test
    void isExistAnyScheduleOutOfDurationFalse() {
        Schedule schedule = new Schedule(NOW, LocalTime.now(), LocalTime.now().plusHours(1));
        Schedules schedules = new Schedules(List.of(schedule));
        Duration duration = new Duration(NOW, TOMORROW);

        assertThat(schedules.hasAnyScheduleOutOfDuration(duration)).isFalse();
    }
}
