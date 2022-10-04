package com.woowacourse.momo.member.domain;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.member.exception.MemberErrorCode;
import com.woowacourse.momo.member.exception.MemberException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {

    /**
     * 사용자 비밀번호는 8자에서 16자여야 하며, 대소문자 포함 영문자와 숫자, 특수문자를 하나 이상 포함해야 한다.
     */
    private static final String PASSWORD_FORMAT = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_FORMAT);

    @Column(name = "password")
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password encrypt(String value, PasswordEncoder encoder) {
        validatePatternIsValid(value);
        return new Password(encoder.encrypt(value));
    }

    public Password update(String value, PasswordEncoder encoder) {
        return Password.encrypt(value, encoder);
    }

    public boolean isSame(String password) {
        return value.equals(password);
    }

    private static boolean isNotValid(String password) {
        return !PASSWORD_PATTERN.matcher(password).matches();
    }

    private static void validatePatternIsValid(String value) {
        if (isNotValid(value)) {
            throw new MemberException(MemberErrorCode.MEMBER_PASSWORD_PATTERN_MUST_BE_VALID);
        }
    }

    public String getValue() {
        return value;
    }
}
