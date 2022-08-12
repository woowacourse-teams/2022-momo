package com.woowacourse.momo.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @DisplayName("회원을 저장한다")
    @Test
    void save() {
        Member member = new Member("aaaa", "1q2w3e4r!", "모모");
        Long id = memberRepository.save(member).getId();

        assertThat(id).isNotNull();
    }

    @DisplayName("식별자를 통해 회원을 조회한다")
    @Test
    void findById() {
        Member member = new Member("aaaa", "1q2w3e4r!", "모모");
        Member expected = memberRepository.save(member);
        synchronize();

        Optional<Member> actual = memberRepository.findById(expected.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("userId과 Password가 일치하는 회원을 조회한다")
    @Test
    void findByUserIdAndPassword() {
        String userId = "aaaa";
        String password = "1q2w3e4r!";
        Member member = new Member(userId, password, "모모");
        Member expected = memberRepository.save(member);
        synchronize();

        Optional<Member> actual = memberRepository.findByUserIdAndPassword(userId, password);

        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    void synchronize() {
        entityManager.flush();
        entityManager.clear();
    }
}
