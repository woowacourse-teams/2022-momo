package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.member.dto.request.SignUpRequest;
import com.woowacourse.momo.member.dto.response.MemberResponse;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원 가입을 한다")
    @Test
    void signUp() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");
        Long id = memberService.signUp(request);

        assertThat(id).isNotNull();
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void findById() {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");
        Long memberId = memberService.signUp(request);

        MemberResponse response = memberService.findById(memberId);

        assertThat(response).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(request);
    }
}
