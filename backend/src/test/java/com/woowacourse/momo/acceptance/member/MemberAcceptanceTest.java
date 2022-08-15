package com.woowacourse.momo.acceptance.member;

import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.acceptance.auth.AuthRestHandler;
import com.woowacourse.momo.fixture.MemberFixture;

class MemberAcceptanceTest extends AcceptanceTest {

    private static final MemberFixture MEMBER = MemberFixture.MOMO;

    private String accessToken;

    @BeforeEach
    void setUp() {
        accessToken = MEMBER.로_로그인한다();
    }

    @DisplayName("개인정보를 조회하다")
    @Test
    void findMyInfo() {
        MemberRestHandler.개인정보를_조회한다(accessToken)
                .statusCode(HttpStatus.OK.value())
                .body("userId", is(MEMBER.getUserId()))
                .body("name", is(MEMBER.getName()));
    }

    @DisplayName("비밀번호를 수정하다")
    @Test
    void updatePassword() {
        String expected = MEMBER.getPassword() + "new";

        MemberRestHandler.비밀번호를_수정한다(accessToken, expected)
                .statusCode(HttpStatus.OK.value());

        AuthRestHandler.로그인을_한다(MEMBER.getUserId(), expected)
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("이름을 수정하다")
    @Test
    void updateName() {
        String expected = MEMBER.getName() + "new";

        MemberRestHandler.이름을_수정한다(accessToken, expected)
                .statusCode(HttpStatus.OK.value());

        MemberRestHandler.개인정보를_조회한다(accessToken)
                .body("name", is(expected));
    }

    @DisplayName("회원탈퇴를 하다")
    @Test
    void delete() {
        MemberRestHandler.회원탈퇴를_한다(accessToken)
                .statusCode(HttpStatus.NO_CONTENT.value());

        // TODO: UNAUTHORIZED 상태코드여야 함.
        AuthRestHandler.로그인을_한다(MEMBER.getUserId(), MEMBER.getPassword())
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
