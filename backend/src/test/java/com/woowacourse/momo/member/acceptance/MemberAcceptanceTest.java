package com.woowacourse.momo.member.acceptance;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import static com.woowacourse.momo.common.acceptance.Fixture.로그인;
import static com.woowacourse.momo.common.acceptance.Fixture.회원_가입;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;
import com.woowacourse.momo.member.dto.request.SignUpRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "1q2w3e4R!";
    private static final String NAME = "모모";

    @Test
    void 회원_가입_테스트() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");

        RestAssuredConvenienceMethod.postRequest(request, "/api/members")
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", startsWith("/api/members"));
    }

    @Test
    void 회원_정보_조회_테스트() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        RestAssuredConvenienceMethod.getRequestWithToken(token, "/api/members/info")
                .statusCode(HttpStatus.OK.value())
                .body("email", is(EMAIL))
                .body("name", is("모모"));
    }

    @Test
    void 회원_탈퇴_테스트() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        RestAssuredConvenienceMethod.deleteRequestWithToken(token, "/api/members/")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
