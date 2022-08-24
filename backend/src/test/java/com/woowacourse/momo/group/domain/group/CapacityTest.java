package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

    @DisplayName("수용 인원이 가득 찼는지 남아있는지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsFullArguments")
    void isFull(int numberOfPeople, boolean expected) {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isFull(numberOfPeople)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsFullArguments() {
        return Stream.of(
                Arguments.of(1, Boolean.TRUE),
                Arguments.of(0, Boolean.FALSE)
        );
    }

    @DisplayName("수용 인원이 참여자 수보다 큰지 확인한다")
    @ParameterizedTest
    @MethodSource("provideIsUnderArguments")
    void isUnder(int numberOfPeople, boolean expected) {
        Capacity capacity = new Capacity(1);

        assertThat(capacity.isUnder(numberOfPeople)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideIsUnderArguments() {
        return Stream.of(
                Arguments.of(2, Boolean.TRUE),
                Arguments.of(0, Boolean.FALSE)
        );
    }
}
