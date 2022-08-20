package com.woowacourse.momo.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId {

    @Column(name = "user_id", nullable = false, unique = true)
    private String value;

    public UserId(String value) {
        this.value = value;
    }

    public void update(String value) {
        this.value = value;
    }
}
