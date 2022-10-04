package com.woowacourse.momo.group.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.exception.GroupException;

class DescriptionTest {

    @DisplayName("모임 설명이 조건에 부합하면 생성한다")
    @ParameterizedTest
    @ValueSource(ints = {1000})
    void construct(int length) {
        String expected = "a".repeat(length);
        Description actual = new Description(expected);

        assertThat(actual.getValue()).isEqualTo(expected);
    }

    @DisplayName("모임 설명 길이가 정책 범위를 벗어나면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1001})
    void nameLengthOutOfRangeException(int length) {
        String description = "a".repeat(length);
        assertThatThrownBy(() -> new Description(description))
                .isInstanceOf(GroupException.class)
                .hasMessage("모임의 설명은 1000자 이하여야 합니다.");
    }
}
