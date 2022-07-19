package com.woowacourse.momo.member.acceptance;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.auth.acceptance.AuthRestHandler;
import com.woowacourse.momo.auth.acceptance.User;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestHandler;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    private static final User USER = User.MOMO;

    private final AuthRestHandler authRestHandler = new AuthRestHandler();

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = authRestHandler.로그인을_하다(USER);
    }

    @Test
    void 회원_정보_조회_테스트() {
        RestHandler.getRequest(accessToken, "/api/members")
                .statusCode(HttpStatus.OK.value())
                .body("email", is(USER.getEmail()))
                .body("name", is(USER.getName()));
    }

    @Test
    void 회원_비밀번호_수정() {
        ChangePasswordRequest request = new ChangePasswordRequest("newPassword1!");
        RestHandler.patchRequest(accessToken, request, "/api/members/password")
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 회원_이름_수정() {
        ChangeNameRequest request = new ChangeNameRequest("새로운 이름");
        RestHandler.patchRequest(accessToken, request, "/api/members/name")
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 회원_탈퇴_테스트() {
        RestHandler.deleteRequest(accessToken, "/api/members")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
