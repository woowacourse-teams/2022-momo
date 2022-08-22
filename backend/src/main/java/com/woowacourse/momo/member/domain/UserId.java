package com.woowacourse.momo.member.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_ID_SHOULD_NOT_BE_BLANK;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.SIGNUP_INVALID_ID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId {

    @Column(name = "user_id", nullable = false, unique = true)
    private String value;

    public UserId(String value) {
        this.value = value;
        validateUserIdIsNotBlank();
        validateUserIdIsValidPattern();
    }

    private void validateUserIdIsNotBlank() {
        if (value.isBlank()) {
            throw new MomoException(MEMBER_ID_SHOULD_NOT_BE_BLANK);
        }
    }

    private void validateUserIdIsValidPattern() {
        if (MemberPattern.isNotValidUserId(value)) {
            throw new MomoException(SIGNUP_INVALID_ID);
        }
    }
}
