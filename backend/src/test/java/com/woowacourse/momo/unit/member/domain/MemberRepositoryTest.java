package com.woowacourse.momo.unit.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.woowacourse.momo.member.domain.member.Member;
import com.woowacourse.momo.member.domain.member.MemberRepository;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("식별자를 통해 회원을 조회한다.")
    @Test
    void findById() {
        Member expected = memberRepository.save(new Member("모모"));

        Optional<Member> actual = memberRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
