package com.woowacourse.momo.fixture;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import com.woowacourse.momo.acceptance.auth.AuthRestHandler;
import com.woowacourse.momo.auth.dto.response.LoginResponse;

@SuppressWarnings("NonAsciiCharacters")
@Getter
public enum MemberFixture {
    
    MOMO("momo@woowa.com", "qwe123!@#", "momo"),
    DUDU("dudu@woowa.com", "qwe123!@#", "dudu")
    ;

    private final String email;
    private final String password;
    private final String name;

    MemberFixture(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String 로_로그인하다() {
        AuthRestHandler.회원가입을_하다(this);
        return AuthRestHandler.로그인을_하다(this)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(LoginResponse.class)
                .getAccessToken();
    }
}
