package com.woowacourse.momo.group.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.group.exception.GroupException;

class GroupNameTest {

    @DisplayName("모임 이름을 생성한다")
    @Test
    void construct() {
        String name = "momo_study";
        GroupName groupName = new GroupName(name);

        assertThat(groupName.getValue()).isEqualTo(name);
    }

    @DisplayName("모임의 이름이 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameMustNotBlank(String name) {
        assertThatThrownBy(() -> new GroupName(name))
                .isInstanceOf(GroupException.class)
                .hasMessage("모임의 이름은 빈 값이 될 수 없습니다.");
    }
}
