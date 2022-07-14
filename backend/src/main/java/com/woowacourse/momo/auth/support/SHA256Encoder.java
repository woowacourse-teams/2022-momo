package com.woowacourse.momo.auth.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

@Component
public class SHA256Encoder implements PasswordEncoder {

    private static final String PASSWORD_ENCRYPT_ALGORITHM = "SHA-256";
    private static final MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance(PASSWORD_ENCRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchElementException("비밀번호 암호화 알고리즘을 찾을 수 없습니다.");
        }
    }

    @Override
    public String encrypt(String text) {
        messageDigest.update(text.getBytes());
        return bytesToHex(messageDigest.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
