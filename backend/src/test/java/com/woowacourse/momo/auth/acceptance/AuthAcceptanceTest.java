package com.woowacourse.momo.auth.acceptance;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;

public class AuthAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture MEMBER_FIXTURE = MemberFixture.MOMO;

    @DisplayName("회원가입을 하다")
    @Test
    void signUp() {
        AuthRestHandler.회원가입을_하다(MEMBER_FIXTURE)
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("로그인을 하다")
    @Test
    void login() {
        AuthRestHandler.회원가입을_하다(MEMBER_FIXTURE);

        AuthRestHandler.로그인을_하다(MEMBER_FIXTURE)
                .statusCode(HttpStatus.OK.value())
                .body("accessToken", Matchers.notNullValue());
    }
}
