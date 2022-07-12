package com.woowacourse.momo.auth.acceptance;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.auth.dto.LoginRequest;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;
import com.woowacourse.momo.member.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "1q2w3e4R!";

    @Test
    public void 로그인() {
        회원_가입(EMAIL, PASSWORD, "모모");

        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);
        RestAssuredConvenienceMethod.postRequest(request, "/api/auth/login")
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue());
    }

    private void 회원_가입(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest(email, password, name);

        RestAssuredConvenienceMethod.postRequest(request, "/api/members");
    }
}
