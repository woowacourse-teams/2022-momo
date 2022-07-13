package com.woowacourse.momo.member.domain;

public interface PasswordEncoder {

    String encrypt(String password);

}
