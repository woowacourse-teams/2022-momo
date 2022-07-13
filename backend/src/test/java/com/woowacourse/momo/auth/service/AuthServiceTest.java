package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.dto.LoginRequest;
import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.service.MemberService;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "wooteco1!";

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, "모모");
        memberService.signUp(request);
    }

    @DisplayName("로그인을 성공한다")
    @Test
    void signIn() {
        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        assertDoesNotThrow(() -> authService.login(request));
    }

    @DisplayName("로그인에 실패한다")
    @Test
    void signInFail() {
        LoginRequest request = new LoginRequest(EMAIL, "wrongPassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(AuthFailException.class);
    }
}
