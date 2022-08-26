package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class UserNameTest {

    @DisplayName("사용자의 이름이 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void nameMustNotBlank(String name) {
        assertThatThrownBy(() -> new UserName(name))
                .isInstanceOf(MomoException.class)
                .hasMessage("사용자의 이름이 빈 값입니다.");
    }

    @DisplayName("사용자의 이름이 30자를 넘어가면 예외가 발생한다")
    @Test
    void nameMustBe30OrLess() {
        assertThatThrownBy(() -> new UserName("a".repeat(31)))
                .isInstanceOf(MomoException.class)
                .hasMessage("사용자의 이름이 30자를 넘습니다.");
    }
}
