package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.dto.SignInRequest;
import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.service.MemberService;

@SpringBootTest
@Transactional
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
        SignInRequest request = new SignInRequest(EMAIL, PASSWORD);

        assertDoesNotThrow(() -> authService.signIn(request));
    }

    @DisplayName("로그인에 실패한다")
    @Test
    void signInFail() {
        SignInRequest request = new SignInRequest(EMAIL, "wrongPassword");

        assertThatThrownBy(() -> authService.signIn(request))
                .isInstanceOf(AuthFailException.class);
    }
}
