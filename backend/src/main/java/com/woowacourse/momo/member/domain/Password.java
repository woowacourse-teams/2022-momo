package com.woowacourse.momo.member.domain;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.ErrorCode;
import com.woowacourse.momo.global.exception.exception.MomoException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    private static final String GHOST_PASSWORD = "";
    private static final String PASSWORD_FORMAT = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_FORMAT);

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

    public Password update(String value, PasswordEncoder encoder) {
        return Password.encrypt(value, encoder);
    }

    public Password delete() {
        return new Password(GHOST_PASSWORD);
    }

    public boolean isSame(String password) {
        return value.equals(password);
    }

    private static boolean isNotValid(String password) {
        return !PASSWORD_PATTERN.matcher(password).matches();
    }

    private static void validatePasswordIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new MomoException(ErrorCode.MEMBER_PASSWORD_SHOULD_NOT_BE_BLANK);
        }
    }

    private static void validatePasswordPatternIsValid(String value) {
        if (isNotValid(value)) {
            throw new MomoException(ErrorCode.MEMBER_PASSWORD_PATTERN_MUST_BE_VALID);
        }
    }
}
