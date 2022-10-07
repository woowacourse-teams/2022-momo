package com.woowacourse.momo.group.domain.participant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.exception.GroupException;

class CapacityTest {

    @DisplayName("Capacity를 생성한다")
    @Test
    void construct() {
        int expected = 10;
        Capacity capacity = new Capacity(expected);

        assertThat(capacity.getValue()).isEqualTo(expected);
    }

    @DisplayName("수용 인원의 범위를 벗어날 경우 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 100})
    void validateCapacityIsInRange(int capacity) {
        assertThatThrownBy(() -> new Capacity(capacity))
                .isInstanceOf(GroupException.class)
                .hasMessage("모임 내 인원은 1명 이상 99명 이하여야 합니다.");
    }

    @DisplayName("주어진 값 이상인지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsFullArguments")
    void isSame(int num, boolean expected) {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isEqualOrOver(num)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsFullArguments() {
        return Stream.of(
                Arguments.of(2, Boolean.TRUE),
                Arguments.of(1, Boolean.TRUE),
                Arguments.of(0, Boolean.FALSE)
        );
    }

    @DisplayName("수용 인원이 참여자 수보다 큰지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsUnderArguments")
    void isSmallThan(int numberOfPeople, boolean expected) {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isSmallThan(numberOfPeople)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsUnderArguments() {
        return Stream.of(
                Arguments.of(2, Boolean.TRUE),
                Arguments.of(0, Boolean.FALSE)
        );
    }
}
