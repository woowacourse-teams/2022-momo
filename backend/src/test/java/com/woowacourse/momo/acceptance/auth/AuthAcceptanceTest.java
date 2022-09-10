package com.woowacourse.momo.acceptance.auth;

import static com.woowacourse.momo.acceptance.auth.AuthRestHandler.로그아웃을_하다;
import static com.woowacourse.momo.acceptance.auth.AuthRestHandler.로그인을_한다;
import static com.woowacourse.momo.acceptance.auth.AuthRestHandler.엑세스토큰을_재발급받는다;
import static com.woowacourse.momo.acceptance.auth.AuthRestHandler.회원가입을_한다;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.fixture.MemberFixture;

@SuppressWarnings("NonAsciiCharacters")
class AuthAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture MEMBER_FIXTURE = MemberFixture.MOMO;

    @DisplayName("회원가입을 하다")
    @Test
    void signUp() {
        회원가입을_한다(MEMBER_FIXTURE)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("로그인을 하다")
    @Test
    void login() {
        회원가입을_한다(MEMBER_FIXTURE);

        로그인을_한다(MEMBER_FIXTURE)
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue())
                .body("refreshToken", Matchers.notNullValue());
    }

    @DisplayName("리프레시토큰을 통해 새로운 엑세스 토큰을 발급받는다")
    @Test
    void reissueAccessToken() {
        회원가입을_한다(MEMBER_FIXTURE);

        String refreshToken = 로그인을_한다(MEMBER_FIXTURE).extract()
                .as(LoginResponse.class).getRefreshToken();

        엑세스토큰을_재발급받는다(refreshToken)
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue());
    }

    @DisplayName("로그아웃을 하다")
    @Test
    void logout() {
        회원가입을_한다(MEMBER_FIXTURE);

        String accessToken = 로그인을_한다(MEMBER_FIXTURE).extract()
                .as(LoginResponse.class).getAccessToken();

        로그아웃을_하다(accessToken)
                .statusCode(HttpStatus.OK.value());
    }
}
