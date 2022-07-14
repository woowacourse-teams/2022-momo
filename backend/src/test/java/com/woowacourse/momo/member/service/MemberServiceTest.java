package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.member.dto.response.MemberResponse;
import com.woowacourse.momo.member.exception.NotFoundMemberException;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @DisplayName("회원 정보를 조회한다")
    @Test
    void findById() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");
        Long memberId = authService.signUp(request);

        MemberResponse response = memberService.findById(memberId);

        assertThat(response).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(request);
    }

    @DisplayName("회원 정보를 삭제한다")
    @Test
    void delete() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");
        Long memberId = authService.signUp(request);

        memberService.deleteById(memberId);

        assertThatThrownBy(() -> memberService.findById(memberId))
                .isInstanceOf(NotFoundMemberException.class);
    }
}
