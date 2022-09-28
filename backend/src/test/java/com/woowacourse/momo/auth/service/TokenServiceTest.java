package com.woowacourse.momo.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.fixture.MemberFixture.DUDU;
import static com.woowacourse.momo.fixture.MemberFixture.MOMO;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.auth.domain.Token;
import com.woowacourse.momo.auth.domain.TokenRepository;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class TokenServiceTest {

    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;
    private final EntityManager entityManager;

    private Member momo;
    private Member dudu;

    @BeforeEach
    void setUp() {
        momo = memberRepository.save(MOMO.toMember());
        dudu = memberRepository.save(DUDU.toMember());
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkluQm9uZyBTb25nIiwiaWF0IjoxNTE2MjM5MDIyfQ.XikBQw8OOU87xoWsYVTJjx6Vpb114WW4FfBoWqqVYHU";
        tokenService.synchronizeRefreshToken(momo, refreshToken);

        synchronize();
    }

    @DisplayName("리프레시 토큰을 저장한다")
    @Test
    void saveRefreshToken() {
        String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkluQm9uZyBTb25nIiwiaWF0IjoxNTE2MjM5MDIyfQ.XikBQw8OOU87xoWsYVTJjx6Vpb114WW4FfBoWqqVYHU";

        tokenService.synchronizeRefreshToken(dudu, refreshToken);
        synchronize();

        Optional<Token> actual = tokenRepository.findByMemberId(dudu.getId());
        assertThat(actual).isPresent();
        assertThat(actual.get().getRefreshToken()).isEqualTo(refreshToken);
    }

    @DisplayName("리프레시 토큰을 업데이트한다")
    @Test
    void updateRefreshToken() {
        String newRefreshToken = "new_bGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkluQm9uZyBTb25nIiwiaWF0IjoxNTE2MjM5MDIyfQ.XikBQw8OOU87xoWsYVTJjx6Vpb114WW4FfBoWqqVYHU";

        tokenService.synchronizeRefreshToken(momo, newRefreshToken);
        synchronize();

        Optional<Token> actual = tokenRepository.findByMemberId(momo.getId());
        assertThat(actual).isPresent();
        assertThat(actual.get().getRefreshToken()).isEqualTo(newRefreshToken);
    }

    @DisplayName("리프레시 토큰을 삭제한다")
    @Test
    void deleteByMemberId() {
        tokenService.deleteByMemberId(momo.getId());
        synchronize();

        Optional<Token> actual = tokenRepository.findByMemberId(momo.getId());
        assertThat(actual).isEmpty();
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
