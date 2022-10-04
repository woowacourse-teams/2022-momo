package com.woowacourse.momo.acceptance.auth;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.fixture.MemberFixture;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
public class AuthRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/auth";

    public static ValidatableResponse 회원가입을_한다(String userId, String password, String name) {
        SignUpRequest request = new SignUpRequest(userId, password, name);
        return postRequest(request, "api/members");
    }

    public static ValidatableResponse 회원가입을_한다(MemberFixture memberFixture) {
        return 회원가입을_한다(memberFixture.getUserId(), memberFixture.getPassword(), memberFixture.getName());
    }

    public static ValidatableResponse 로그인을_한다(String userId, String password) {
        LoginRequest request = new LoginRequest(userId, password);
        return postRequest(request, BASE_URL + "/login");
    }

    public static ValidatableResponse 로그인을_한다(MemberFixture memberFixture) {
        return 로그인을_한다(memberFixture.getUserId(), memberFixture.getPassword());
    }

    public static ValidatableResponse 엑세스토큰을_재발급받는다(String refreshToken) {
        return postRequest(refreshToken, BASE_URL + "/token/refresh");
    }

    public static ValidatableResponse 로그아웃을_하다(String accessToken) {
        return postRequest(accessToken, BASE_URL + "/logout");
    }
}
