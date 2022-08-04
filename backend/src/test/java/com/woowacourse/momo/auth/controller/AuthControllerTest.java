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

    private static final String USER_ID = "woowa";
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
        SignUpRequest request = new SignUpRequest(USER_ID, PASSWORD, NAME);

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

    @DisplayName("잘못된 아이디 형식으로 로그인시 400코드가 반환된다")
    @Test
    void signUpWithInvalidUserIdPattern() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@", PASSWORD, NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("SIGNUP_ERROR_001")));
    }

    @DisplayName("비어있는 아이디 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankUserId() throws Exception {
        SignUpRequest request = new SignUpRequest("", PASSWORD, NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    @DisplayName("비어있는 비밀번호 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankPassword() throws Exception {
        SignUpRequest request = new SignUpRequest(USER_ID, "", NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    @DisplayName("잘못된 비밀번호 패턴으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithInvalidPasswordPattern() throws Exception {
        SignUpRequest request = new SignUpRequest(USER_ID, "woowa", NAME);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    @DisplayName("비어있는 이름 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankName() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa", PASSWORD, "");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    @DisplayName("정상적으로 로그인될 시 토큰이 발급된다")
    @Test
    void login() throws Exception {
        createNewMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);

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

    @DisplayName("비어있는 아이디 값으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankUserId() throws Exception {
        createNewMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("", PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    @DisplayName("비어있는 비밀번호 형식으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankPassword() throws Exception {
        createNewMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("woowa", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("VALIDATION_ERROR_001")));
    }

    void createNewMember(String userId, String password, String name) {
        SignUpRequest request = new SignUpRequest(userId, password, name);
        authService.signUp(request);
    }
}
