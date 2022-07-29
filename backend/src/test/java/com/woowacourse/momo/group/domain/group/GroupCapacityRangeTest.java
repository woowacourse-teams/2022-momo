package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GroupCapacityRangeTest {

    @ParameterizedTest
    @DisplayName("모임 정원은 1명 이상 99명 이하여야 한다.")
    @ValueSource(ints = {-1, 0, 100})
    void isOutOfRange(int capacity) {
        boolean actual = GroupCapacityRange.isOutOfRange(capacity);

        assertThat(actual).isTrue();
    }
}
