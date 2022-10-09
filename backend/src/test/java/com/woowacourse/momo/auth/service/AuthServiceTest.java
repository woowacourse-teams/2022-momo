package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.domain.Token;
import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.response.AccessTokenResponse;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.exception.MemberException;
import com.woowacourse.momo.member.service.MemberFindService;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class AuthServiceTest {

    private static final String USER_ID = "woowa";
    private static final String PASSWORD = "wooteco1!";
    private static final String NAME = "모모";

    private final TokenRepository tokenRepository;
    private final AuthService authService;
    private final MemberService memberService;
    private final MemberFindService memberFindService;

    @DisplayName("로그인을 성공한다")
    @Test
    void login() {
        createMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);

        LoginResponse response = authService.login(request);

        assertAll(
                () -> assertThat(response.getAccessToken()).isNotNull(),
                () -> assertThat(response.getRefreshToken()).isNotNull()
        );
    }

    @DisplayName("DB에 저장된 리프레시 토큰이 없을 경우 새로운 토큰 정보를 저장한다")
    @Test
    void loginWithoutTokenStoredInDB() {
        Long memberId = createMember(USER_ID, PASSWORD, NAME);
        Optional<Token> before = tokenRepository.findByMemberId(memberId);

        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);
        authService.login(request);
        Optional<Token> after = tokenRepository.findByMemberId(memberId);

        assertAll(
                () -> assertThat(before).isEmpty(),
                () -> assertThat(after).isPresent()
        );
    }

    @DisplayName("DB에 저장된 리프레시 토큰이 있을 경우 새로운 토큰 정보로 갱신한다")
    @Test
    void loginWithTokenStoredInDB() {
        String refreshTokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        Long memberId = createMember(USER_ID, PASSWORD, NAME);
        Member member = memberFindService.findMember(memberId);
        Token token = new Token(member, refreshTokenValue);
        tokenRepository.save(token);

        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);
        authService.login(request);
        Token actual = tokenRepository.findByMemberId(member.getId()).orElseThrow();

        assertThat(actual.getRefreshToken()).isNotEqualTo(refreshTokenValue);
    }

    @DisplayName("로그인에 실패한다")
    @Test
    void loginFail() {
        createMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, "wrong123!");

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(MemberException.class)
                .hasMessage("아이디나 비밀번호가 다릅니다.");
    }

    @DisplayName("Access Token을 재발급한다")
    @Test
    void reissueAccessToken() {
        Long memberId = createMember(USER_ID, PASSWORD, NAME);
        AccessTokenResponse response = authService.reissueAccessToken(memberId);

        assertThat(response.getAccessToken()).isNotNull();
    }

    Long createMember(String userId, String password, String name) {
        SignUpRequest request = new SignUpRequest(userId, password, name);
        return memberService.signUp(request);
    }
}
