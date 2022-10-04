package com.woowacourse.momo.auth.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.woowacourse.momo.auth.exception.AuthException;

class JwtTokenProviderTest {

    private String secretKey = "eyasfQciOiJIUzI1NiIvLoAR5cCI6IkpXVCJ9.eyJzdWIiOiIiLCLoCP1lIjoiSm9obiBEV9UiLCJpYXBCusE1MTYyMzkwMjJ9.123asdfa8s7d6f987qweahqwculaoxce80k1i2o387tg";
    private JwtTokenProvider tokenProvider = new JwtTokenProvider(secretKey, 0, 0);

    @DisplayName("토큰의 유효 기간이 만료되었을 경우 예외가 발생한다")
    @Test
    void validateTokenExpiration() {
        String accessToken = tokenProvider.createAccessToken(1L);

        assertThatThrownBy(() -> tokenProvider.validateTokenNotUsable(accessToken))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("토큰의 유효기간이 만료되었습니다.");
    }

    @DisplayName("애플리케이션에서 생성한 토큰이 아니면 예외가 발생한다")
    @Test
    void validateInvalidToken() {
        String accessToken = "asdaxlckvhlasfjh";

        boolean actual = tokenProvider.validateTokenNotUsable(accessToken);
        assertThat(actual).isTrue();
    }
}
