package com.woowacourse.momo.auth.acceptance;

import lombok.Getter;

@Getter
public enum User {
    
    MOMO("momo@woowa.com", "qwe123!@#", "momo")
    ;

    private final String email;
    private final String password;
    private final String name;

    User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
