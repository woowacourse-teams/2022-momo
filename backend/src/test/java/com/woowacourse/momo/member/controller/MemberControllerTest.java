package com.woowacourse.momo.member.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.member.dto.request.SignUpRequest;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("정상적으로 회원가입이 되는 경우를 테스트한다.")
    @Test
    void signUp() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "모모");

        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(header().string("location", "/api/members/1"));
    }

    @DisplayName("잘못된 이메일 형식으로 회원가입시 400코드가 반환된다.")
    @Test
    void signUpWithInvalidEmailPattern() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("잘못된 이메일 형식입니다.")));
    }

    @DisplayName("비어있는 이메일 값으로 회원가입시 400코드가 반환된다.")
    @Test
    void signUpWithBlankEmail() throws Exception {
        SignUpRequest request = new SignUpRequest("", "wooteco1!", "모모");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("이메일은 빈 값일 수 없습니다.")));
    }

    @DisplayName("비어있는 비밀번호 값으로 회원가입시 400코드가 반환된다.")
    @Test
    void signUpWithBlankPassword() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "", "모모");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("패스워드는 빈 값일 수 없습니다.")));
    }

    @DisplayName("비어있는 이름 값으로 회원가입시 400코드가 반환된다.")
    @Test
    void signUpWithBlankName() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", "wooteco1!", "");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", containsString("이름은 빈 값일 수 없습니다.")));
    }
}
