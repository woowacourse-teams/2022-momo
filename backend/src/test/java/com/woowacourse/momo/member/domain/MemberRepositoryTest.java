package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 저장한다.")
    @Test
    void save() {
        Member member = new Member("aa@bb.com", "1q2w3e4r!", "모모");
        Long id = memberRepository.save(member).getId();

        assertThat(id).isNotNull();
    }

    @DisplayName("식별자를 통해 회원을 조회한다.")
    @Test
    void findById() {
        Member member = new Member("aa@bb.com", "1q2w3e4r!", "모모");
        Member expected = memberRepository.save(member);

        Optional<Member> actual = memberRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
