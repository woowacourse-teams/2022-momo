package com.woowacourse.momo.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

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

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted;

    public Member(String userId, Password password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = new Name(name);
    }

    public boolean isNotSamePassword(String password) {
        return !this.password.equals(password);
    }

    public void changePassword(String password) {
        this.password.update(password);
    }

    public void changeName(String name) {
        this.name.update(name);
    }

    public void delete() {
        password.update(GHOST_PRIVATE_INFO);
        name.update(GHOST_NAME);
        deleted = true;
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getName() {
        return name.getValue();
    }
}
