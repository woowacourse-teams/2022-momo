package com.woowacourse.momo.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    private static final String GHOST_NAME = "알 수 없음";
    private static final String GHOST_PRIVATE_INFO = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Member(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void delete() {
        userId = GHOST_PRIVATE_INFO;
        password = GHOST_PRIVATE_INFO;
        name = GHOST_NAME;
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
