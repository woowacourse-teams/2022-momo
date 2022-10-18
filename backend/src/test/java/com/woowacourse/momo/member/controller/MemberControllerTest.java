package com.woowacourse.momo.member.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class MemberControllerTest {

    private static final String ID = "momo";
    private static final String PASSWORD = "1q2W3e4r!";
    private static final String NAME = "모모";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AuthService authService;

    @Autowired
    MemberService memberService;

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
                .andExpect(jsonPath("id", notNullValue()))
                .andExpect(jsonPath("userId", is(ID)))
                .andDo(
                        document("memberFind",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("로그인하지 않고 사용자를 조회하는 경우 예외가 발생한다")
    @Test
    void findNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("정상적으로 비밀번호를 수정한 경우를 테스트한다")
    @Test
    void updatePassword() throws Exception {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("newPassword123!", PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members/password")
                        .header("authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(
                        document("memberUpdatePassword",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("비밀번호를 수정할 때 현재 비밀번호가 잘못된 경우 예외가 발생한다")
    @Test
    void updatePasswordInvalidOldPassword() throws Exception {

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("newPassword123!", "aabbcc");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members/password")
                        .header("authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("정상적으로 이름을 수정한 경우를 테스트한다")
    @Test
    void updateName() throws Exception {
        ChangeNameRequest nameRequest = new ChangeNameRequest("무무");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members/name")
                        .header("authorization", "bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(nameRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andDo(
                        document("memberUpdateName",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("정상적으로 사용자를 삭제한 경우를 테스트한다.")
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members")
                        .header("authorization", "bearer " + accessToken))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andDo(
                        document("memberDelete",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    String login() {
        LoginRequest loginRequest = new LoginRequest(ID, PASSWORD);
        return authService.login(loginRequest).getAccessToken();
    }

    void signUp() {
        SignUpRequest signUpRequest = new SignUpRequest(ID, PASSWORD, NAME);

        memberService.signUp(signUpRequest);
    }
}
