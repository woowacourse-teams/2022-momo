package com.woowacourse.momo.group.domain.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.global.exception.exception.MomoException;

class DeadlineTest {

    private static final Duration DURATION = new Duration(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));

    @DisplayName("마감 기한이 현재 시간보다 이전일 경우 예외가 발생한다")
    @Test
    void deadlineMustBeBeforeNow() {
        assertThatThrownBy(() -> new Deadline(LocalDateTime.now().minusDays(1), DURATION))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 과거일 수 없습니다.");
    }

    @DisplayName("마감 기한이 기간의 시작 일자보다 이전일 경우 예외가 발생한다")
    @Test
    void deadlineMustBeBeforeStartDuration() {
        assertThatThrownBy(() -> new Deadline(LocalDateTime.of(DURATION.getStartDate().plusDays(1), LocalTime.now()), DURATION))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 시작 일자 이후일 수 없습니다.");
    }

    @DisplayName("마감 기한이 지났는지 확인한다")
    @Test
    void isOver() {
        Deadline deadline = new Deadline(LocalDateTime.now().plusDays(1), DURATION);

        assertThat(deadline.isOver()).isFalse();
    }
}
