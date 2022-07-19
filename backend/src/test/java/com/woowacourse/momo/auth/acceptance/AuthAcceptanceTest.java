package com.woowacourse.momo.auth.acceptance;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;

public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "qwe123!@#";
    private static final String NAME = "MOMO";

    private final AuthRestHandler authRestHandler = new AuthRestHandler();

    @DisplayName("회원가입을 하다")
    @Test
    void signUp() {
        authRestHandler.회원가입을_하다(EMAIL, PASSWORD, NAME)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("로그인을 하다")
    @Test
    void login() {
        authRestHandler.회원가입을_하다(EMAIL, PASSWORD, NAME);

        authRestHandler.로그인을_하다(EMAIL, PASSWORD)
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue());
    }
}
