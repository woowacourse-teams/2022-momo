package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import static com.woowacourse.momo.fixture.MemberFixture.DUDU;

import org.junit.jupiter.api.Test;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.fixture.MemberFixture;

class MemberTest {

    private static final PasswordEncoder passwordEncoder = new SHA256Encoder();
    private static final MemberFixture MEMBER = DUDU;

    @Test
    void construct() {
        Member member = MEMBER.toMember();

        assertAll(
                () -> assertThat(member.getUserId()).isEqualTo(MEMBER.getUserId()),
                () -> {
                    Password password = Password.encrypt(MEMBER.getPassword(), passwordEncoder);
                    assertThat(member.getPassword()).isEqualTo(password.getValue());
                },
                () -> assertThat(member.getUserName()).isEqualTo(MEMBER.getName())
        );
    }

    @Test
    void changePassword() {
        Member member = MEMBER.toMember();

        String password = MEMBER.getPassword() + "123";
        member.changePassword(password, passwordEncoder);

        Password expected = Password.encrypt(password, passwordEncoder);
        assertThat(member.getPassword()).isEqualTo(expected.getValue());
    }

    @Test
    void delete() {
        Member member = MEMBER.toMember();
        member.delete();

        assertAll(
                () -> assertThat(member.getUserId()).isEmpty(),
                () -> assertThat(member.getPassword()).isEmpty(),
                () -> assertThat(member.getUserName()).isEmpty()
        );
    }

    @Test
    void isSameUserId() {
        Member member1 = MEMBER.toMember();
        Member member2 = MEMBER.toMember();

        assertThat(member1.isSameUserId(member2)).isTrue();
    }

    @Test
    void isNotSamePassword() {
        Member member = MEMBER.toMember();

        String password = MEMBER.getPassword();
        assertThat(member.isNotSamePassword(password)).isTrue();
    }
}
