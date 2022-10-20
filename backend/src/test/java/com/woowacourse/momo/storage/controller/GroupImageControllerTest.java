package com.woowacourse.momo.storage.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.woowacourse.momo.fixture.ImageFixture.PNG_IMAGE;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.woowacourse.momo.auth.service.AuthService;
import com.woowacourse.momo.auth.service.dto.request.LoginRequest;
import com.woowacourse.momo.member.service.MemberService;
import com.woowacourse.momo.member.service.dto.request.SignUpRequest;
import com.woowacourse.momo.storage.service.GroupImageService;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class GroupImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @MockBean
    private GroupImageService groupImageService;

    private static final MockMultipartFile IMAGE = PNG_IMAGE.toMultipartFile();

    @DisplayName("정상적으로 모임 이미지를 수정한다")
    @Test
    void update() throws Exception {
        Long saveMemberId = saveMember();
        String accessToken = accessToken();
        String fullPath = "https://image.moyeora.site/group/saved/imageName.png";
        BDDMockito.given(groupImageService.update(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
                .willReturn(fullPath);

        mockMvc.perform(multipart("/api/groups/1/thumbnail")
                        .file(IMAGE)
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpectAll(
                        status().isCreated(),
                        header().string("location", equalTo(fullPath)))
                .andDo(
                        document("groupImageUpdate",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("정상적으로 모임 이미지를 초기화한다")
    @Test
    void init() throws Exception {
        Long saveMemberId = saveMember();
        String accessToken = accessToken();
        BDDMockito.willDoNothing()
                .given(groupImageService)
                .init(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(delete("/api/groups/1/thumbnail")
                        .header("Authorization", "bearer " + accessToken)
                )
                .andExpect(status().isNoContent())
                .andDo(
                        document("groupImageInit",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    Long saveMember() {
        SignUpRequest request = new SignUpRequest("woowa", "wooteco1!", "모모");
        return memberService.signUp(request);
    }

    String accessToken() {
        LoginRequest request = new LoginRequest("woowa", "wooteco1!");

        return authService.login(request).getAccessToken();
    }
}
