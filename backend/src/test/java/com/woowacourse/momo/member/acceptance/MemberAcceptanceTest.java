package com.woowacourse.momo.member.acceptance;

import static org.hamcrest.Matchers.is;

import static com.woowacourse.momo.common.acceptance.Fixture.로그인;
import static com.woowacourse.momo.common.acceptance.Fixture.회원_가입;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestHandler;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "1q2w3e4R!";
    private static final String NAME = "모모";

    @Test
    void 회원_정보_조회_테스트() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        RestHandler.getRequest(token, "/api/members")
                .statusCode(HttpStatus.OK.value())
                .body("email", is(EMAIL))
                .body("name", is("모모"));
    }

    @Test
    void 회원_비밀번호_수정() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        ChangePasswordRequest request = new ChangePasswordRequest("newPassword1!");
        RestHandler.patchRequest(token, request, "/api/members/password")
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 회원_이름_수정() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        ChangeNameRequest request = new ChangeNameRequest("새로운 이름");
        RestHandler.patchRequest(token, request, "/api/members/name")
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 회원_탈퇴_테스트() {
        회원_가입(EMAIL, PASSWORD, NAME);
        String token = 로그인(EMAIL, PASSWORD);

        RestHandler.deleteRequest(token, "/api/members")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
