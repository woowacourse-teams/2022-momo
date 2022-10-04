package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.woowacourse.momo.member.exception.MemberException;

class UserNameTest {

    @DisplayName("사용자 이름이 조건에 부합하면 생성한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 20})
    void construct(int length) {
        String expected = "a".repeat(length);
        UserName userName = UserName.from(expected);

        assertThat(userName.getValue()).isEqualTo(expected);
    }

    @DisplayName("사용자의 이름이 빈값이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1, 20})
    void nameMustNotBlank(int length) {
        String name = " ".repeat(length);
        assertThatThrownBy(() -> UserName.from(name))
                .isInstanceOf(MemberException.class)
                .hasMessage("사용자의 이름이 공백입니다.");
    }

    @DisplayName("사용자의 이름이 길이 정책을 벗어나면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {21})
    void lengthOutOfRangeException(int length) {
        String name = "a".repeat(length);
        assertThatThrownBy(() -> UserName.from(name))
                .isInstanceOf(MemberException.class)
                .hasMessage("사용자 이름은 1자 이상 20자 이하여야 합니다.");
    }
}
