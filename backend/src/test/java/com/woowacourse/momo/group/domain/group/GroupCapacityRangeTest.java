package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class GroupCapacityRangeTest {

    @DisplayName("정원의 수가 범위를 벗어나면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100})
    void validateCapacityIsInRange(int capacity) {
        assertThatThrownBy(() -> GroupCapacityRange.validateCapacityIsInRange(capacity))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임 내 인원은 1명 이상 99명 이하여야 합니다.");
    }
}
