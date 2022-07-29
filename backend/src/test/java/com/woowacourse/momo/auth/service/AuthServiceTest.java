package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.exception.AuthFailException;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "wooteco1!";
    private static final String NAME = "모모";

    @Autowired
    private AuthService authService;

    @DisplayName("회원 가입을 한다")
    @Test
    void signUp() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NAME);
        Long id = authService.signUp(request);

        assertThat(id).isNotNull();
    }

    @DisplayName("로그인을 성공한다")
    @Test
    void login() {
        createMember(EMAIL, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        assertThat(authService.login(request).getAccessToken()).isNotNull();
    }

    @DisplayName("로그인에 실패한다")
    @Test
    void loginFail() {
        createMember(EMAIL, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(EMAIL, "wrongPassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(AuthFailException.class);
    }

    void createMember(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest(email, password, name);
        authService.signUp(request);
    }
}
