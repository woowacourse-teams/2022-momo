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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class))
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final UserId USER_ID = UserId.momo("momo");
    private static final UserName USER_NAME = UserName.from("모모");

    private Password password;
    private Member member;

    @BeforeEach
    void setUp() {
        password = Password.encrypt("1q2w3e4r!", new SHA256Encoder());
        member = memberRepository.save(new Member(USER_ID, password, USER_NAME));

        String refreshTokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        Token token = new Token(this.member, refreshTokenValue);

        tokenRepository.save(token);
    }

    @DisplayName("리프레시 토큰 정보를 저장한다")
    @Test
    void save() {
        String refreshTokenValue = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        Password password = Password.encrypt("1q2w3e4r!", new SHA256Encoder());
        Member member = memberRepository.save(new Member(UserId.momo("member"), password, USER_NAME));
        Token token = new Token(member, refreshTokenValue);
        tokenRepository.save(token);

        Optional<Token> actual = tokenRepository.findByMemberId(member.getId());
        assertThat(actual).isPresent();
    }

    @DisplayName("회원 아이디를 통해 리프레시토큰을 가져온다")
    @Test
    void findByMemberId() {
        Optional<Token> actual = tokenRepository.findByMemberId(member.getId());

        assertThat(actual).isPresent();
    }

    @DisplayName("회원 아이디를 통해 해당 회원의 리프레시 토큰을 삭제한다")
    @Test
    void deleteByMemberId() {
        tokenRepository.deleteByMemberId(member.getId());
        Optional<Token> actual = tokenRepository.findByMemberId(member.getId());

        assertThat(actual).isEmpty();
    }
}
