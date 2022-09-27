package com.woowacourse.momo.member.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class GhostMember {

    private static final String GHOST_PASSWORD = "";

    private final UserId userId;
    private final UserName userName;
    private final Password password;

    public GhostMember() {
        this.userId = createUserId();
        this.userName = createUserName();
        this.password = createPassword();
    }

    private UserId createUserId() {
        return UserId.deletedAs(createRandomValue());
    }

    private UserName createUserName() {
        return UserName.deletedAs(createRandomValue());
    }

    private Password createPassword() {
        return Password.deletedAs(GHOST_PASSWORD);
    }

    private String createRandomValue() {
        return UUID.randomUUID().toString();
    }
}
