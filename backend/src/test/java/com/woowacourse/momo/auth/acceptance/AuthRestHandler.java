package com.woowacourse.momo.auth.acceptance;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.dto.response.LoginResponse;
import com.woowacourse.momo.common.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class AuthRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/auth";

    public ValidatableResponse 회원가입을_하다(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest(email, password, name);
        return postRequest(request, BASE_URL + "/signup");
    }

    public ValidatableResponse 로그인을_하다(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return postRequest(request, BASE_URL + "/login");
    }
    
    public String 로그인을_하다(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();

        회원가입을_하다(email, password, name);
        return 로그인을_하다(email, password).extract()
                .as(LoginResponse.class)
                .getAccessToken();
    }
}
