package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.global.exception.exception.MomoException;

class CalendarTest {

    private static final LocalDate YESTERDAY_DATE = LocalDate.now().minusDays(1);
    private static final LocalDate AFTER_TWO_DATE = LocalDate.now().plusDays(2);
    private static final LocalDate AFTER_THREE_DATE = LocalDate.now().plusDays(3);
    private static final LocalDateTime TOMORROW_DATETIME = LocalDateTime.now().plusDays(1);

    @DisplayName("마감 기한이 지났는지 확인한다")
    @Test
    void isOver() {
        Duration duration = new Duration(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));
        Calendar calendar = new Calendar(new ArrayList<>(), duration, new Deadline(TOMORROW_DATETIME));

        assertThat(calendar.isDeadlineOver()).isFalse();
    }

    @DisplayName("마감 기한이 기간의 시작 일자보다 이전일 경우 예외가 발생한다")
    @Test
    void deadlineMustBeBeforeStartDuration() {
        Duration duration = new Duration(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));

        assertThatThrownBy(() -> new Calendar(new ArrayList<>(), duration, new Deadline(LocalDateTime.of(duration.getStartDate().plusDays(1), LocalTime.now()))))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }

    @DisplayName("일정들이 기간 내에 속해있지 않을 경우 예외가 발생한다")
    @Test
    void validateSchedulesAreInDuration() {
        Schedule schedule = new Schedule(YESTERDAY_DATE, LocalTime.now(), LocalTime.now().plusHours(1));
        Duration duration = new Duration(AFTER_TWO_DATE, AFTER_THREE_DATE);

        assertThatThrownBy(() -> new Calendar(List.of(schedule), duration, new Deadline(TOMORROW_DATETIME)))
                .isInstanceOf(MomoException.class)
                .hasMessage("일정이 모임 기간에 포함되어야 합니다.");
    }
}
