package com.woowacourse.momo.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId {

    private static final int MINIMUM_LENGTH = 4;
    private static final int MAXIMUM_LENGTH = 50;
    private static final String EMAIL_FORMAT = "@";

    @Column(name = "user_id", unique = true, length=50)
    private String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId momo(String value) {
        validateNotBlank(value);
        validateLengthInRange(value);
        validateNotEmailPattern(value);
        return new UserId(value);
    }

    public static UserId oauth(String value) {
        validateEmailPattern(value);
        validateLengthInRange(value);
        return new UserId(value);
    }

    private static void validateNotBlank(String value) {
        if (value.isBlank()) {
            throw new MemberException(MemberErrorCode.USER_ID_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validateLengthInRange(String userId) {
        int length = userId.length();
        if (length < MINIMUM_LENGTH || MAXIMUM_LENGTH < length) {
            throw new MemberException(MemberErrorCode.USER_ID_CANNOT_BE_OUT_OF_RANGE);
        }
    }

    private static void validateNotEmailPattern(String value) {
        if (value.contains(EMAIL_FORMAT)) {
            throw new MemberException(MemberErrorCode.SIGNUP_INVALID_ID);
        }
    }

    private static void validateEmailPattern(String value) {
        if (!value.contains(EMAIL_FORMAT)) {
            throw new MemberException(MemberErrorCode.USER_ID_SHOULD_BE_EMAIL_FORMAT);
        }
    }

    public String getValue() {
        return value;
    }
}
