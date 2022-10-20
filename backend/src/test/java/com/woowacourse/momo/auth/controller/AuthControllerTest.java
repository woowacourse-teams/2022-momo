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
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

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

    @Autowired
    private MemberService memberService;

    @DisplayName("정상적으로 회원가입이 되는 경우를 테스트한다")
    @Test
    void signUp() throws Exception {
        SignUpRequest request = new SignUpRequest(USER_ID, PASSWORD, NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andDo(
                        document("memberSignup",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("잘못된 아이디 형식으로 로그인시 400코드가 반환된다")
    @Test
    void signUpWithInvalidUserIdPattern() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa@", PASSWORD, NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("SIGNUP_001")));
    }

    @DisplayName("길이 형식을 벗어난 아이디 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithIdOutOfLengthRange() throws Exception {
        SignUpRequest request = new SignUpRequest("abc", PASSWORD, NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_013")));
    }

    @DisplayName("비어있는 아이디 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankUserId() throws Exception {
        SignUpRequest request = new SignUpRequest(" ".repeat(4), PASSWORD, NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_010")));
    }

    @DisplayName("비어있는 비밀번호 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankPassword() throws Exception {
        SignUpRequest request = new SignUpRequest(USER_ID, "", NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_008")));
    }

    @DisplayName("잘못된 비밀번호 패턴으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithInvalidPasswordPattern() throws Exception {
        SignUpRequest request = new SignUpRequest(USER_ID, "woowa", NAME);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_008")));
    }

    @DisplayName("길이 정책을 벗어난 이름 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithNameOutOfLengthRange() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa", PASSWORD, "a".repeat(21));

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_006")));
    }

    @DisplayName("비어있는 이름 값으로 회원가입시 400코드가 반환된다")
    @Test
    void signUpWithBlankName() throws Exception {
        SignUpRequest request = new SignUpRequest("woowa", PASSWORD, " ");

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_005")));
    }

    @DisplayName("정상적으로 로그인될 시 토큰이 발급된다")
    @Test
    void login() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(USER_ID, PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("accessToken", notNullValue()))
                .andDo(
                        document("memberLogin",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("길이 형식을 벗어난 아이디 값으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithIdOutOfLengthRange() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("abc", PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_013")));
    }

    @DisplayName("비어있는 아이디 값으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankUserId() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest(" ".repeat(4), PASSWORD);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_010")));
    }

    @DisplayName("비어있는 비밀번호 형식으로 로그인시 400코드가 반환된다")
    @Test
    void loginWithBlankPassword() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        LoginRequest request = new LoginRequest("woowa", "");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", containsString("MEMBER_008")));
    }

    @DisplayName("리프레시 토큰을 통해 엑세스 토큰을 재발급받는다")
    @Test
    void reissueAccessToken() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        String refreshToken = extractLoginResponse(USER_ID, PASSWORD).getRefreshToken();

        mockMvc.perform(post("/api/auth/token/refresh")
                        .header("Authorization", "bearer " + refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken", notNullValue()))
                .andDo(
                        document("reissueToken",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("리프레시 토큰 없이 재발급 요청을 하면 에러가 발생한다")
    @Test
    void reissueWithoutAccessToken() throws Exception {
        mockMvc.perform(post("/api/auth/token/refresh"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("로그아웃을 한다")
    @Test
    void logout() throws Exception {
        saveMember(USER_ID, PASSWORD, NAME);
        String accessToken = extractLoginResponse(USER_ID, PASSWORD).getAccessToken();

        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "bearer " + accessToken))
                .andExpect(status().isOk())
                .andDo(
                        document("memberLogout",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    void saveMember(String userId, String password, String name) {
        SignUpRequest request = new SignUpRequest(userId, password, name);
        memberService.signUp(request);
    }

    LoginResponse extractLoginResponse(String userId, String password) {
        LoginRequest request = new LoginRequest(userId, password);

        return authService.login(request);
    }
}
