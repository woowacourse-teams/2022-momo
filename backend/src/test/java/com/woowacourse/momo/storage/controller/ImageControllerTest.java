package com.woowacourse.momo.storage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.woowacourse.momo.storage.service.StorageService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ImageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @DisplayName("유저 이미지를 저장한다")
    @Test
    void userImageUploadTest() throws Exception {
        when(storageService.saveMemberImage(any(), any())).thenReturn("1.png");

        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "1.png",
                MediaType.IMAGE_PNG_VALUE,
                "abcdefgh".getBytes()
        );

        mockMvc.perform(multipart("/api/images/members/1").file(file))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Location", "/api/images/members/1.png"))
                .andDo(
                        document("userimageupload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("모임 이미지를 저장한다")
    @Test
    void groupImageUploadTest() throws Exception {
        when(storageService.saveGroupImage(any(), any())).thenReturn("1.png");

        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "1.png",
                MediaType.IMAGE_PNG_VALUE,
                "abcdefgh".getBytes()
        );

        mockMvc.perform(multipart("/api/images/groups/1").file(file))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Location", "/api/images/groups/1.png"))
                .andDo(
                        document("groupimageupload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("유저의 이미지를 불러온다")
    @Test
    void userImageLoadTest() throws Exception {
        when(storageService.loadMemberImage(1L)).thenReturn("abcdefgh".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/members/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().bytes("abcdefgh".getBytes()))
                .andDo(
                        document("userimageload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("모임의 이미지를 불러온다")
    @Test
    void groupImageLoadTest() throws Exception {
        when(storageService.loadGroupImage(1L)).thenReturn("abcdefgh".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/groups/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().bytes("abcdefgh".getBytes()))
                .andDo(
                        document("groupimageload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }
}
