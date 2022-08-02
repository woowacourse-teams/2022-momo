package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;
import com.woowacourse.momo.member.exception.NotFoundMemberException;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.service.dto.response.MyInfoResponse;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @DisplayName("회원 정보를 조회한다")
    @Test
    void findById() {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");
        Long memberId = authService.signUp(request);

        MyInfoResponse response = memberService.findById(memberId);

        assertAll(
                () -> assertThat(response.getId()).isEqualTo(memberId),
                () -> assertThat(response.getUserId()).isEqualTo(request.getUserId()),
                () -> assertThat(response.getName()).isEqualTo(request.getName())
        );
    }

    @DisplayName("회원 비밀번호를 수정한다")
    @Test
    void updatePassword() {
        Long memberId = createMember();
        Member beforeMember = memberRepository.findById(memberId).get();
        String beforePassword = beforeMember.getPassword();

        ChangePasswordRequest request = new ChangePasswordRequest("wooteco2!");
        memberService.updatePassword(memberId, request);

        Member member = memberRepository.findById(memberId).get();
        assertThat(member.getPassword()).isNotEqualTo(beforePassword);
    }

    @DisplayName("회원 이름을 수정한다")
    @Test
    void updateName() {
        Long memberId = createMember();
        Member beforeMember = memberRepository.findById(memberId).get();
        String beforeName = beforeMember.getName();

        ChangeNameRequest request = new ChangeNameRequest("무무");
        memberService.updateName(memberId, request);

        Member member = memberRepository.findById(memberId).get();
        assertThat(member.getName()).isNotEqualTo(beforeName);
    }

    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        Long memberId = createMember();

        memberService.deleteById(memberId);

        assertThatThrownBy(() -> memberService.findById(memberId))
                .isInstanceOf(NotFoundMemberException.class);
    }

    private Long createMember() {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");
        return authService.signUp(request);
    }
}
