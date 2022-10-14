package com.woowacourse.momo.member.domain;

import java.util.Optional;

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
import lombok.ToString;

import com.woowacourse.momo.auth.support.PasswordEncoder;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    private static final String SHOWN_DELETED_USER_NAME = "";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserId userId;

    @Embedded
    private Password password;

    @Embedded
    private UserName userName;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean deleted;

    public Member(UserId userId, Password password, UserName userName) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }

    public boolean isNotSamePassword(String password) {
        return !this.password.isSame(password);
    }

    public void changePassword(String password, PasswordEncoder encoder) {
        this.password = this.password.update(password, encoder);
    }

    public void changeUserName(String userName) {
        this.userName = this.userName.update(userName);
    }

    public void delete() {
        userId = null;
        password = null;
        userName = null;
        deleted = true;
    }

    public boolean isSameUserId(Member member) {
        return userId.getValue().equals(member.userId.getValue());
    }

    public String getUserId() {
        return Optional.ofNullable(userId)
                .map(UserId::getValue)
                .orElse("");
    }

    public String getPassword() {
        return Optional.ofNullable(password)
                .map(Password::getValue)
                .orElse("");
    }

    public String getUserName() {
        return Optional.ofNullable(userName)
                .map(UserName::getValue)
                .orElse("");
    }
}
