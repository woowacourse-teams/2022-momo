package com.woowacourse.momo.group.domain.calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.global.exception.exception.MomoException;

class DeadlineTest {

    @DisplayName("마감 기한이 현재 시간보다 이전일 경우 예외가 발생한다")
    @Test
    void deadlineMustBeBeforeNow() {
        assertThatThrownBy(() -> new Deadline(LocalDateTime.now().minusDays(1)))
                .isInstanceOf(MomoException.class)
                .hasMessage("마감시간이 과거일 수 없습니다.");
    }

    @DisplayName("마감 기한이 지났는지 확인한다")
    @Test
    void isPast() {
        Deadline deadline = new Deadline(LocalDateTime.now().plusDays(1));

        assertThat(deadline.isPast()).isFalse();
    }
}
