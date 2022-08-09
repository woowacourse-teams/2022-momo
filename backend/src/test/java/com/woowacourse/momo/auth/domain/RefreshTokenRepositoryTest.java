package com.woowacourse.momo.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final Long memberId = 1L;

    @BeforeEach
    void setUp() {
        String refreshTokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        RefreshToken refreshToken = new RefreshToken(memberId, refreshTokenValue);

        refreshTokenRepository.save(refreshToken);
    }

    @DisplayName("리프레시 토큰 정보를 저장한다")
    @Test
    void save() {
        Long memberId = 2L;
        String refreshTokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        RefreshToken refreshToken = new RefreshToken(memberId, refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        boolean actual = refreshTokenRepository.existsByMemberId(memberId);
        assertThat(actual).isTrue();
    }

    @DisplayName("회원 아이디를 통해 리프레시토큰을 가져온다")
    @Test
    void findByMemberId() {
        Optional<RefreshToken> actual = refreshTokenRepository.findByMemberId(memberId);

        assertThat(actual).isPresent();
    }

    @DisplayName("회원 아이디를 통해 해당 회원의 리프레시 토큰이 있는지 확인한다")
    @Test
    void existsByMemberId() {
        boolean actual = refreshTokenRepository.existsByMemberId(memberId);

        assertThat(actual).isTrue();
    }

    @DisplayName("회원 아이디를 통해 해당 회원의 리프레시 토큰을 삭제한다")
    @Test
    void deleteByMemberId() {
        refreshTokenRepository.deleteByMemberId(memberId);
        boolean actual = refreshTokenRepository.existsByMemberId(memberId);

        assertThat(actual).isFalse();
    }
}
