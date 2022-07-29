package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GroupCapacityRangeTest {

    @DisplayName("유효하지 않은 모임 정원 값이면 True를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100})
    void isOutOfRange(int capacity) {
        boolean actual = GroupCapacityRange.isOutOfRange(capacity);

        assertThat(actual).isTrue();
    }

    @DisplayName("유효한 모임 정원 값이면 False를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 99})
    void isInOfRange(int capacity) {
        boolean actual = GroupCapacityRange.isOutOfRange(capacity);

        assertThat(actual).isFalse();
    }
}
