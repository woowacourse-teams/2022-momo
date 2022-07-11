package com.woowacourse.momo.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.member.dto.request.SignUpRequest;

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
}
