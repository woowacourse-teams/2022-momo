package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberPatternTest {

    @DisplayName("사용자의 아이디가 적절한 형식이 아닐 경우 True 를 반환한다")
    @Test
    void isNotValidUserIdTrue() {
        assertThat(MemberPattern.isNotValidUserId("email@wooawacourse.com")).isTrue();
    }

    @DisplayName("사용자의 아이디가 적절한 형식일 경우 False 를 반환한다")
    @Test
    void isNotValidUserIdFalse() {
        assertThat(MemberPattern.isNotValidUserId("asdf")).isFalse();
    }

    @DisplayName("사용자의 비밀번호가 적절한 형식이 아닐 경우 True 를 반환한다")
    @Test
    void isNotValidPasswordTrue() {
        assertThat(MemberPattern.isNotValidPassword("a")).isTrue();
    }

    @DisplayName("사용자의 비밀번호가 적절한 형식일 경우 False 를 반환한다")
    @Test
    void isNotValidPasswordFalse() {
        assertThat(MemberPattern.isNotValidPassword("asdf1234!")).isFalse();
    }
}
