package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class CapacityTest {

    @DisplayName("수용 인원의 범위를 벗어날 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {0, 100})
    void overCapacityRange(int capacity) {
        assertThatThrownBy(() -> new Capacity(capacity))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임 내 인원은 1명 이상 99명 이하여야 합니다.");
    }

    @DisplayName("수용 인원이 가득 찼을 경우 True 를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void isFullTrue(int numberOfPeople) {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isFull(numberOfPeople)).isTrue();
    }

    @DisplayName("수용 인원이 남아있을 경우 False 를 반환한다")
    @Test
    void isFullFalse() {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isFull(0)).isFalse();
    }
}
