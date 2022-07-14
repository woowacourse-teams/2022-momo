package com.woowacourse.momo.auth.support;

public interface PasswordEncoder {

    String encrypt(String password);
}
