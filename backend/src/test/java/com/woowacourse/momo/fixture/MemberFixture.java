package com.woowacourse.momo.fixture;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.acceptance.auth.AuthRestHandler;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum MemberFixture {

    MOMO("momo", "qwe123!@#", "모모"),
    DUDU("dudu", "qwe123!@#", "두두"),
    GUGU("gugu", "qwe123!@#", "구구")
    ;

    private final String userId;
    private final String password;
    private final String name;

    MemberFixture(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String 로_로그인한다() {
        AuthRestHandler.회원가입을_한다(this);
        return AuthRestHandler.로그인을_한다(this)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(LoginResponse.class)
                .getAccessToken();
    }
}
