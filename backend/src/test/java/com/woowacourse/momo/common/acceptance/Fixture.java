package com.woowacourse.momo.common.acceptance;

import com.woowacourse.momo.auth.dto.LoginRequest;
import com.woowacourse.momo.member.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
public class Fixture {

    public static void 회원_가입(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest(email, password, name);

        RestAssuredConvenienceMethod.postRequest(request, "/api/members");
    }

    public static String 로그인(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);

        return RestAssuredConvenienceMethod.postRequest(request, "/api/auth/login")
                .extract()
                .jsonPath().getString("accessToken");
    }
}
