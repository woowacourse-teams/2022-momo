package com.woowacourse.momo.auth.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class OauthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("정상적으로 Oauth 인증 링크가 반환되는지 테스트한다")
    @Test
    void access() throws Exception {
        mockMvc.perform(get("/api/auth/oauth2/google/login"))
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
        String code = "code";
        mockMvc.perform(get("/api/auth/oauth2/google/login")
                        .queryParam("code", code))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("accessToken", notNullValue()))
                .andDo(
                        document("oauthlogin",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("code").description("code")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("인증 토큰")
                                )

                        )
                );
    }
}
