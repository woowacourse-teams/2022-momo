package com.woowacourse.momo.group.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.exception.GroupException;

class GroupNameTest {

    @DisplayName("모임 이름이 조건에 부합하면 생성한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 50})
    void construct(int nameLength) {
        String name = "a".repeat(nameLength);
        GroupName groupName = new GroupName(name);
        assertThat(groupName.getValue()).isEqualTo(name);
    }

    @DisplayName("모임의 이름이 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 50})
    void nameMustNotBlank(int length) {
        String name = " ".repeat(length);
        assertThatThrownBy(() -> new GroupName(name))
                .isInstanceOf(GroupException.class)
                .hasMessage("모임의 이름은 공백이 될 수 없습니다.");
    }

    @DisplayName("모임 이름 길이가 정책 범위를 벗어나면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {51})
    void nameLengthOutOfRangeException(int nameLength) {
        String name = "a".repeat(nameLength);
        assertThatThrownBy(() -> new GroupName(name))
                .isInstanceOf(GroupException.class)
                .hasMessage("모임의 이름은 1자 이상 50자 이하여야 합니다.");
    }
}
