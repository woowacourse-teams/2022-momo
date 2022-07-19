package com.woowacourse.momo.member.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.dto.request.LoginRequest;
import com.woowacourse.momo.auth.dto.request.SignUpRequest;
import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.member.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.dto.request.ChangePasswordRequest;


@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class MemberControllerTest {

    private static final String ID = "momo@woowa.com";
    private static final String PASSWORD = "1q2W3e4r!";
    private static final String NAME = "모모";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    String accessToken;

    @BeforeEach
    void setUp() {
        signUp();
        accessToken = login();
    }

    @DisplayName("정상적으로 사용자를 조회한 경우를 테스트한다")
    @Test
    void find() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members")
                .header("authorization", "bearer " + accessToken))
                .andExpect(jsonPath("email", is(ID)));
    }

    @DisplayName("정상적으로 비밀번호를 수정한 경우를 테스트한다")
    @Test
    void updatePassword() throws Exception {
        ChangePasswordRequest passwordRequest = new ChangePasswordRequest("1q2wW34R!");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members/password")
                        .header("authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("정상적으로 이름을 수정한 경우를 테스트한다")
    @Test
    void updateName() throws Exception {
        ChangeNameRequest nameRequest = new ChangeNameRequest("무무");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members/name")
                        .header("authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(nameRequest)))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("정상적으로 사용자를 삭제한 경우를 테스트한다.")
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members")
                        .header("authorization", "bearer " + accessToken))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }

    String login() {
        LoginRequest loginRequest = new LoginRequest(ID, PASSWORD);
        return authService.login(loginRequest).getAccessToken();
    }

    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest(ID, PASSWORD, NAME);

        authService.signUp(signUpRequest);
    }
}
