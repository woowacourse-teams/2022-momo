package com.woowacourse.momo.auth.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.momo.auth.service.OauthService;
import com.woowacourse.momo.auth.service.dto.response.LoginResponse;
import com.woowacourse.momo.auth.service.dto.response.OauthLinkResponse;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OauthService oauthService;

    @DisplayName("정상적으로 Oauth 인증 링크가 반환되는지 테스트한다")
    @Test
    void access() throws Exception {
        String oauthLink = "https://accounts.google.com/o/oauth2/auth?redirect_uri=http://www.moyeora.com/auth/google&scope=openid+https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile&response_type=code&client_id=clientId";
        BDDMockito.given(oauthService.generateAuthUrl(ArgumentMatchers.anyString()))
                .willReturn(new OauthLinkResponse(oauthLink));

        mockMvc.perform(get("/api/auth/oauth2/google/login")
                        .queryParam("redirectUrl", "https://www.moyeora.com/auth/google"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("oauthLink", notNullValue()))
                .andDo(
                        document("oauthLink",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("정상적으로 로그인될 시 토큰이 발급된다")
    @Test
    void login() throws Exception {
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU5OTI3MTMxLCJleHAiOjE2NTk5MzA3MzF9.VG8BIv3X1peT0e16OdSqq4EkkgDd1bHbYX99oglxkS4";
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjAwMjk0OTUsImV4cCI6MTY2MDAzMzA5NX0.qwxal9Fp78G5l6RWbG9SMvOVnb0pnrEkWPHMPBmQw8c";
        BDDMockito.given(oauthService.requestAccessToken(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .willReturn(new LoginResponse(accessToken, refreshToken));

        String code = "code";
        mockMvc.perform(get("/api/auth/oauth2/google/login")
                        .queryParam("code", code)
                        .queryParam("redirectUrl", "https://www.moyeora.com/auth/google"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("accessToken", notNullValue()),
                        jsonPath("refreshToken", notNullValue()))
                .andDo(
                        document("oauthLogin",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }
}
