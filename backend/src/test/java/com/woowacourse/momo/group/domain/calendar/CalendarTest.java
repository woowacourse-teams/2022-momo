package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.global.exception.exception.MomoException;

class CalendarTest {

    @DisplayName("마감 기한이 지났는지 확인한다")
    @Test
    void isOver() {
        Duration duration = new Duration(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));
        Calendar calendar = new Calendar(new ArrayList<>(), duration, LocalDateTime.now().plusDays(1));

        assertThat(calendar.isDeadlineOver()).isFalse();
    }

    @DisplayName("마감 기한이 기간의 시작 일자보다 이전일 경우 예외가 발생한다")
    @Test
    void deadlineMustBeBeforeStartDuration() {
        Duration duration = new Duration(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));

        assertThatThrownBy(() -> new Calendar(new ArrayList<>(), duration, LocalDateTime.of(duration.getStartDate().plusDays(1), LocalTime.now())))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }
}
