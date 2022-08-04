package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.exception.AuthFailException;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.globalException.exception.MomoException;

@Transactional
@SpringBootTest
class AuthServiceTest {

    private static final String USER_ID = "woowa";
    private static final String PASSWORD = "wooteco1!";
    private static final String NAME = "모모";

    @Autowired
    private AuthService authService;

    @DisplayName("회원 가입을 한다")
    @Test
    void signUp() {
        SignUpRequest request = new SignUpRequest(USER_ID, PASSWORD, NAME);
        Long id = authService.signUp(request);

        assertThat(id).isNotNull();
    }

    @DisplayName("로그인을 성공한다")
    @Test
    void login() {
        createMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);

        assertThat(authService.login(request).getAccessToken()).isNotNull();
    }

    @DisplayName("로그인에 실패한다")
    @Test
    void loginFail() {
        createMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, "wrongPassword");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(MomoException.class)
                .hasMessage("아이디나 비밀번호가 다릅니다.");
    }

    void createMember(String userId, String password, String name) {
        SignUpRequest request = new SignUpRequest(userId, password, name);
        authService.signUp(request);
    }
}
