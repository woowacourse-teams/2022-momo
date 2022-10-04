package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.member.exception.MemberException;

class UserIdTest {

    @DisplayName("사용자 아이디가 조건에 부합하면 생성한다")
    @ParameterizedTest
    @ValueSource(ints = {4, 50})
    void construct(int length) {
        String id = "a".repeat(length);
        UserId userId = UserId.momo(id);
        assertThat(userId.getValue()).isEqualTo(id);
    }

    @DisplayName("사용자의 아이디가 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 51, 52})
    void lengthOutOfRangeException(int length) {
        String id = "a".repeat(length);
        assertThatThrownBy(() -> UserId.momo(id))
                .isInstanceOf(MemberException.class)
                .hasMessage("사용자 아이디는 4자 이상 50자 이하여야 합니다.");
    }

    @DisplayName("사용자의 아이디가 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {4, 50})
    void idMustNotBlank(int length) {
        String id = " ".repeat(length);
        assertThatThrownBy(() -> UserId.momo(id))
                .isInstanceOf(MemberException.class)
                .hasMessage("사용자의 아이디가 공백입니다.");
    }

    @DisplayName("사용자의 아이디가 이메일 형식일 경우 예외가 발생한다")
    @Test
    void idMustNotBeInEmail() {
        assertThatThrownBy(() -> UserId.momo("id@woowacourse.com"))
                .isInstanceOf(MemberException.class)
                .hasMessage("잘못된 형식의 아이디입니다.");
    }

    @DisplayName("구글 사용자의 아이디가 이메일 형식이 아닐 경우 예외가 발생한다")
    @Test
    void googleIdMustBeInEmail() {
        assertThatThrownBy(() -> UserId.oauth("id"))
                .isInstanceOf(MemberException.class)
                .hasMessage("구글 아이디가 이메일 형식이 아닙니다.");
    }
}
