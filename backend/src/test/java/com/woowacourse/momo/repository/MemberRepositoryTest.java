package com.woowacourse.momo.repository;

import com.woowacourse.momo.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("식별자를 통해 회원을 조회한다.")
    @Test
    void findById() {
        Member member = new Member("모모");
        Member expected = memberRepository.save(member);

        Optional<Member> actual = memberRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                        .isEqualTo(expected);
    }
}
