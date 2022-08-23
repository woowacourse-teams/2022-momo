package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class UserIdTest {

    @DisplayName("사용자의 아이디가 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void idMustNotBlank(String id) {
        assertThatThrownBy(() -> UserId.momo(id))
                .isInstanceOf(MomoException.class)
                .hasMessage("사용자의 아이디가 빈 값입니다.");
    }

    @DisplayName("사용자의 아이디가 이메일 형식일 경우 예외가 발생한다")
    @Test
    void idMustNotBeInEmail() {
        assertThatThrownBy(() -> UserId.momo("id@woowacourse.com"))
                .isInstanceOf(MomoException.class)
                .hasMessage("잘못된 형식의 아이디입니다.");
    }

    @DisplayName("구글 사용자의 아이디가 이메일 형식이 아닐 경우 예외가 발생한다")
    @Test
    void googleIdMustBeInEmail() {
        assertThatThrownBy(() -> UserId.oauth("id"))
                .isInstanceOf(MomoException.class)
                .hasMessage("구글 아이디가 이메일 형식이 아닙니다.");
    }
}
