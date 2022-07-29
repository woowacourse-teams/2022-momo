package com.woowacourse.momo.auth.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.auth.service.dto.request.SignUpRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class AuthControllerTest {

    private static final String EMAIL = "woowa@woowa.com";
    private static final String PASSWORD = "wooteco1!";
    private static final String NAME = "모모";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @DisplayName("정상적으로 회원가입이 되는 경우를 테스트한다")
    @Test
    void signUp() throws Exception {
        SignUpRequest request = new SignUpRequest(EMAIL, PASSWORD, NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("membersignup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("잘못된 이메일 형식으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithInvalidEmailPattern() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa", PASSWORD, NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("잘못된 이메일 형식입니다.")));
    }

    @DisplayName("비어있는 이메일 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankEmail() throws Exception {
        SignUpRequest request = new SignUpRequest("", PASSWORD, NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("이메일은 빈 값일 수 없습니다.")));
    }

    @DisplayName("비어있는 비밀번호 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankPassword() throws Exception {
        SignUpRequest request = new SignUpRequest(EMAIL, "", NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("패스워드는 빈 값일 수 없습니다.")));
    }

    @DisplayName("잘못된 비밀번호 패턴으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithInvalidPasswordPattern() throws Exception {
        SignUpRequest request = new SignUpRequest(EMAIL, "woowa", NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("패스워드는 영문자와 하나 이상의 숫자, 특수 문자를 갖고 있어야 합니다.")));
    }

    @DisplayName("비어있는 이름 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankName() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@woowa.com", PASSWORD, "");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("이름은 빈 값일 수 없습니다.")));
    }

    @DisplayName("정상적으로 로그인될 시 토큰이 발급된다")
    @Test
    void login() throws Exception {
        createNewMember(EMAIL, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(EMAIL, PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("accessToken", notNullValue()))
                .andDo(
                        document("memberlogin",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("잘못된 이메일 형식으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithInvalidEmailPattern() throws Exception {
        LoginRequest request = new LoginRequest("woowa", PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("잘못된 이메일 형식입니다.")));
    }

    @DisplayName("비어있는 이메일 값으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankEmail() throws Exception {
        createNewMember(EMAIL, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("", PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("이메일은 빈 값일 수 없습니다.")));
    }

    @DisplayName("비어있는 비밀번호 형식으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankPassword() throws Exception {
        createNewMember(EMAIL, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("woowa@woowa.com", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("패스워드는 빈 값일 수 없습니다.")));
    }

    void createNewMember(String email, String password, String name) {
        SignUpRequest request = new SignUpRequest(email, password, name);
        authService.signUp(request);
    }
}
