package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.member.exception.MemberException;

class PasswordTest {

    private final PasswordEncoder ENCODER = new SHA256Encoder();

    @DisplayName("사용자의 비밀번호는 올바른 패턴 형식이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {" ", "1234567!", "asdfghj!", "asdf1234", "12345678", "asdfghjk", "!@#$%^&*", "a1!",
            "123456789asdfgh!@#$"})
    void passwordMustBeValidPattern(String password) {
        assertThatThrownBy(() -> Password.encrypt(password, ENCODER))
                .isInstanceOf(MemberException.class)
                .hasMessage("패스워드는 영문자와 하나 이상의 숫자, 특수 문자를 갖고 있어야 합니다.");
    }
}
