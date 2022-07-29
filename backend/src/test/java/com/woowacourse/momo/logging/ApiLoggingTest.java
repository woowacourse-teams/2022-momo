package com.woowacourse.momo.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.MemberFixture.INVALID_EMAIL;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.acceptance.auth.AuthRestHandler;
import com.woowacourse.momo.auth.service.AuthService;

class ApiLoggingTest extends AcceptanceTest {

    private static ByteArrayOutputStream console;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
        console = new ByteArrayOutputStream();
        System.setOut(new PrintStream(console));
    }

    @DisplayName("method 실행 전 후에 로그를 남긴다")
    @Test
    void infoLog() {
        executeInfoLogMethod();

        assertAll(
                () -> assertThat(console.toString()).contains("com.woowacourse.momo.logging.Logging"),
                () -> assertThat(console.toString()).contains("com.woowacourse.momo.auth.controller.AuthController/signUp"),
                () -> assertThat(console.toString()).contains("INFO")
        );
    }

    @DisplayName("예측한 예외 발생 시 로그를 남긴다")
    @Test
    void warningLog() {
        executeWarningLogMethod();

        assertAll(
                () -> assertThat(console.toString()).contains("com.woowacourse.momo.logging.Logging"),
                () -> assertThat(console.toString()).contains("com.woowacourse.momo.globalException.ControllerAdvice"),
                () -> assertThat(console.toString()).contains("WARN")
        );
    }

    private void executeInfoLogMethod() {
        AuthRestHandler.회원가입을_한다(MOMO);
    }

    private void executeWarningLogMethod() {
        AuthRestHandler.회원가입을_한다(INVALID_EMAIL);
    }
}