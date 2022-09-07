package com.woowacourse.momo.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.global.exception.exception.GlobalErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId {

    private static final String EMAIL_FORMAT = "@";

    @Column(name = "user_id", nullable = false, unique = true)
    private String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId momo(String value) {
        validateUserIdIsNotBlank(value);
        validateUserIdIsValidPattern(value);
        return new UserId(value);
    }

    public static UserId oauth(String value) {
        validateUserEmailIsValidPattern(value);
        return new UserId(value);
    }

    private static void validateUserIdIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(GlobalErrorCode.MEMBER_ID_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validateUserIdIsValidPattern(String value) {
        if (value.contains(EMAIL_FORMAT)) {
            throw new MomoException(GlobalErrorCode.SIGNUP_INVALID_ID);
        }
    }

    private static void validateUserEmailIsValidPattern(String value) {
        if (!value.contains(EMAIL_FORMAT)) {
            throw new MomoException(GlobalErrorCode.GOOGLE_ID_SHOULD_BE_IN_EMAIL_FORMAT);
        }
    }
}
