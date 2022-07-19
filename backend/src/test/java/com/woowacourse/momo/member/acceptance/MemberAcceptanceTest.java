package com.woowacourse.momo.member.acceptance;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.auth.acceptance.AuthRestHandler;
import com.woowacourse.momo.auth.acceptance.User;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestHandler;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;

public class MemberAcceptanceTest extends AcceptanceTest {

    private static final User USER = User.MOMO;

    private final AuthRestHandler authRestHandler = new AuthRestHandler();

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = authRestHandler.로그인을_하다(USER);
    }

    @DisplayName("개인정보를 조회하다")
    @Test
    void findMyInfo() {
        RestHandler.getRequest(accessToken, "/api/members")
                .statusCode(HttpStatus.OK.value())
                .body("email", is(USER.getEmail()))
                .body("name", is(USER.getName()));
    }

    @DisplayName("비밀번호를 수정하다")
    @Test
    void updatePassword() {
        ChangePasswordRequest request = new ChangePasswordRequest("newPassword1!");
        RestHandler.patchRequest(accessToken, request, "/api/members/password")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름을 수정하다")
    @Test
    void updateName() {
        ChangeNameRequest request = new ChangeNameRequest("새로운 이름");
        RestHandler.patchRequest(accessToken, request, "/api/members/name")
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원탈퇴를 하다")
    @Test
    void delete() {
        RestHandler.deleteRequest(accessToken, "/api/members")
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
