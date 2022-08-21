package com.woowacourse.momo.member.domain;

import java.util.regex.Pattern;

public class MemberPattern {

    private static final String EMAIL_FORMAT = "@";
    private static final String PASSWORD_FORMAT = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_FORMAT);

    private MemberPattern() {
    }

    public static boolean isNotValidUserId(String userId) {
        return userId.contains(EMAIL_FORMAT);
    }

    public static boolean isNotValidPassword(String password) {
        return !PASSWORD_PATTERN.matcher(password).matches();
    }
}
