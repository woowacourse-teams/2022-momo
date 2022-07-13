package com.woowacourse.momo.auth.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.dto.LoginRequest;
import com.woowacourse.momo.member.domain.Member;
import com.woowacourse.momo.member.domain.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Member member = new Member("woowa@woowa.com", "Woowa1!", "모모");
        memberRepository.save(member);
    }

    @DisplayName("정상적으로 로그인될 시 토큰이 발급된다.")
    @Test
    void login() throws Exception {
        LoginRequest request = new LoginRequest("woowa@woowa.com", "Woowa1!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @DisplayName("잘못된 이메일 형식으로 로그인시 400코드가 반환된다.")
    @Test
    void loginWithInvalidEmailPattern() throws Exception {
        LoginRequest request = new LoginRequest("woowa", "Woowa1!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("잘못된 이메일 형식입니다.")));
    }

    @DisplayName("비어있는 이메일 값으로 로그인시 400코드가 반환된다.")
    @Test
    void loginWithBlankEmail() throws Exception {
        LoginRequest request = new LoginRequest("", "Woowa1!");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("이메일은 빈 값일 수 없습니다.")));
    }

    @DisplayName("비어있는 비밀번호 형식으로 로그인시 400코드가 반환된다.")
    @Test
    void loginWithBlankPassword() throws Exception {
        LoginRequest request = new LoginRequest("woowa@woowa.com", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("패스워드는 빈 값일 수 없습니다.")));
    }
}
