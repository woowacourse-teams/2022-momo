package com.woowacourse.momo.auth.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

@Component
public class SHA256Encoder implements PasswordEncoder {

    private static final String PASSWORD_ENCRYPT_ALGORITHM = "SHA-256";

    @Override
    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCRYPT_ALGORITHM);
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchElementException("비밀번호 암호화 알고리즘을 찾을 수 없습니다.");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
