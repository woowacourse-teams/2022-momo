package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.support.SHA256Encoder;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.domain.Password;
import com.woowacourse.momo.member.domain.UserId;
import com.woowacourse.momo.member.domain.UserName;
import com.woowacourse.momo.member.exception.MemberException;

@Transactional
@SpringBootTest
class MemberFindServiceTest {

    @Autowired
    private MemberFindService memberFindService;

    @Autowired
    private MemberRepository memberRepository;

    private static final UserId USER_ID = UserId.momo("momo");
    private static final UserName USER_NAME = UserName.from("모모");
    private static final Password PASSWORD = Password.encrypt("momo123!", new SHA256Encoder());

    @DisplayName("회원을 조회한다")
    @Test
    void findMember() {
        Member expected = memberRepository.save(new Member(USER_ID, PASSWORD, USER_NAME));

        Member actual = memberFindService.findMember(expected.getId());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findNotExistMember() {
        assertThatThrownBy(() -> memberFindService.findMember(1000L))
                .isInstanceOf(MemberException.class)
                .hasMessage("멤버가 존재하지 않습니다.");
    }

    @DisplayName("삭제된 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findDeletedMember() {
        Member member = memberRepository.save(new Member(USER_ID, PASSWORD, USER_NAME));
        member.delete();

        assertThatThrownBy(() -> memberFindService.findMember(member.getId()))
                .isInstanceOf(MemberException.class)
                .hasMessage("탈퇴한 멤버입니다.");
    }

    @DisplayName("아이디와 비밀번호로 회원을 조회한다")
    @Test
    void findMemberByIdAndPassword() {
        Member member = memberRepository.save(new Member(USER_ID, PASSWORD, USER_NAME));

        Member foundMember = memberFindService.findByUserIdAndPassword(USER_ID, PASSWORD);
        assertThat(foundMember).usingRecursiveComparison()
                .isEqualTo(member);
    }

    @DisplayName("잘못된 아이디와 비밀번호로 회원을 조회하는 경우 예외가 발생한다")
    @Test
    void findMemberByIdAndWrongPassword() {
        Member member = memberRepository.save(new Member(USER_ID, PASSWORD, USER_NAME));

        Password wrongPassword = Password.encrypt("wrong123!", new SHA256Encoder());
        assertThatThrownBy(
                () -> memberFindService.findByUserIdAndPassword(USER_ID, wrongPassword)
        ).isInstanceOf(MemberException.class)
                .hasMessageContaining("아이디나 비밀번호가 다릅니다.");
    }
}
