package com.woowacourse.momo.group.domain.group;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class GroupNameTest {

    @DisplayName("모임의 이름이 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameMustNotBlank(String name) {
        assertThatThrownBy(() -> new GroupName(name))
                .isInstanceOf(MomoException.class)
                .hasMessage("모임의 이름이 빈 값입니다.");
    }
}
