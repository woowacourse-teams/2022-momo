package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.woowacourse.momo.fixture.MemberFixture.MOMO;
import static com.woowacourse.momo.member.exception.MemberErrorCode.MEMBER_NOT_EXIST;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.exception.MemberException;

@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest
class MemberValidatorTest {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    @DisplayName("존재하지 않는 회원 id가 아니면 예외를 발생시킨다")
    @Test
    void validateExistMember() {
        Long notExistId = 100L;
        assertThatThrownBy(() -> memberValidator.validateExistMember(notExistId))
                .isInstanceOf(MemberException.class)
                .hasMessage(MEMBER_NOT_EXIST.getMessage());
    }

    @DisplayName("삭제된 않는 회원 id가 아니면 예외를 발생시킨다")
    @Test
    void validateDeletedMember() {
        Member member = memberRepository.save(MOMO.toMember());
        member.delete();

        assertThatThrownBy(() -> memberValidator.validateExistMember(member.getId()))
                .isInstanceOf(MemberException.class)
                .hasMessage(MEMBER_NOT_EXIST.getMessage());
    }
}
