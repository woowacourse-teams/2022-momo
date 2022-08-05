package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
class MemberFindServiceTest {

    @Autowired
    private MemberFindService memberFindService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 조회한다")
    @Test
    void findMember() {
        Member expected = memberRepository.save(new Member("momo", "qwe123!@#", "momo"));

        Member actual = memberFindService.findMember(expected.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
