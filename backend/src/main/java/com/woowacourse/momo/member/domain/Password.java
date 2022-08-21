package com.woowacourse.momo.member.domain;

import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_PASSWORD_PATTERN_MUST_BE_VALID;
import static com.woowacourse.momo.global.exception.exception.ErrorCode.MEMBER_PASSWORD_SHOULD_NOT_BE_BLANK;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password encrypt(String value, PasswordEncoder encoder) {
        validatePasswordIsNotBlank(value);
        validatePasswordPatternIsValid(value);
        return new Password(encoder.encrypt(value));
    }

    public void update(String value) {
        this.value = value;
    }

    public boolean isSame(String password) {
        return value.equals(password);
    }

    private static void validatePasswordIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(MEMBER_PASSWORD_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validatePasswordPatternIsValid(String value) {
        if (MemberPattern.isNotValidPassword(value)) {
            throw new MomoException(MEMBER_PASSWORD_PATTERN_MUST_BE_VALID);
        }
    }
}
