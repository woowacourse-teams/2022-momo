package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.global.exception.exception.MomoException;

class UserIdTest {

    @DisplayName("사용자의 아이디가 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void idMustNotBlank(String id) {
        assertThatThrownBy(() -> new UserId(id))
                .isInstanceOf(MomoException.class)
                .hasMessage("사용자의 아이디가 빈 값입니다.");
    }
}