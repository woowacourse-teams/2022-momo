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
import lombok.ToString;

import com.woowacourse.momo.auth.support.PasswordEncoder;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

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

    public Member(UserId userId, Password password, String userName) {
        this(userId, password, new UserName(userName));
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
        password = password.delete();
        userName = userName.delete();
        deleted = true;
    }

    public boolean isSameUserId(Member member) {
        return userId.equals(member.userId);
    }

    public String getUserId() {
        return userId.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getUserName() {
        return userName.getValue();
    }
}
