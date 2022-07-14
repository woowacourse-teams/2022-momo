package com.woowacourse.momo.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SHA256EncoderTest {

    private final PasswordEncoder passwordEncoder = new SHA256Encoder();

    @DisplayName("SHA256 알고리즘으로 암호화를 진행한다면 길이가 64인 문자열이 생성된다")
    @Test
    void encrypt() {
        String plainText = "aabb";
        String cipherText = passwordEncoder.encrypt(plainText);

        assertThat(cipherText).hasSize(64);
    }
}
