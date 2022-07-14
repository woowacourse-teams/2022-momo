package com.woowacourse.momo.auth.acceptance;

import static com.woowacourse.momo.common.acceptance.Fixture.회원_가입;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "1q2w3e4R!";

    @Test
    void 회원_가입_테스트() {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, "모모");

        RestAssuredConvenienceMethod.postRequest(request, "/api/auth/signup")
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void 로그인_테스트() {
        회원_가입(EMAIL, PASSWORD, "모모");

        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);
        RestAssuredConvenienceMethod.postRequest(request, "/api/auth/login")
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue());
    }
}
