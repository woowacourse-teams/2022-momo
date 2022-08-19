package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.PasswordEncoder;
import com.woowacourse.momo.global.exception.exception.MomoException;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@Transactional
@SpringBootTest
class MemberFindServiceTest {

    @Autowired
    private MemberFindService memberFindService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원을 조회한다")
    @Test
    void findMember() {
        Member expected = memberRepository.save(new Member("momo", "qwe123!@#", "momo"));

        Member actual = memberFindService.findMember(expected.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findNotExistMember() {
        assertThatThrownBy(() -> memberFindService.findMember(1000L))
                .isInstanceOf(MomoException.class)
                .hasMessage("멤버가 존재하지 않습니다.");
    }

    @DisplayName("삭제된 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findDeletedMember() {
        Member member = memberRepository.save(new Member("momo", "qwe123!@#", "momo"));
        member.delete();

        assertThatThrownBy(() -> memberFindService.findMember(member.getId()))
                .isInstanceOf(MomoException.class)
                .hasMessage("탈퇴한 멤버입니다.");
    }

    @DisplayName("아이디와 비밀번호로 회원을 조회한다")
    @Test
    void findMemberByIdAndPassword() {
        Member member = memberRepository.save(new Member("momo", "qwe123!@#", "모모몽"));

        Member foundMember = memberFindService.findByUserIdAndPassword("momo", "qwe123!@#");
        assertThat(foundMember).usingRecursiveComparison()
                .isEqualTo(member);
    }

    @DisplayName("잘못된 아이디와 비밀번호로 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findMemberByIdAndWrongPassword() {
        Member member = memberRepository.save(new Member("momo", "qwe123!@#", "모모몽"));

        assertThatThrownBy(
                () -> memberFindService.findByUserIdAndPassword("momo", "wrongpassword")
        ).isInstanceOf(MomoException.class)
                .hasMessageContaining("아이디나 비밀번호가 다릅니다.");
    }
}
