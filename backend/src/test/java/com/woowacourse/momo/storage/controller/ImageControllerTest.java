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

    @DisplayName("이미지를 업로드한다")
    @Test
    void imageUploadTest() throws Exception {
        when(storageService.save(any())).thenReturn("abc.png");

        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "abc.png",
                MediaType.IMAGE_PNG_VALUE,
                "abcdefgh".getBytes()
        );

        mockMvc.perform(multipart("/api/images/").file(file))
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("Location", "/api/images/abc.png"))
                .andDo(
                        document("imageupload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }

    @DisplayName("이미지를 불러온다")
    @Test
    void imageLoadTest() throws Exception {
        when(storageService.load("abc.png")).thenReturn("abcdefgh".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/abc.png"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().bytes("abcdefgh".getBytes()))
                .andDo(
                        document("imageload",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint())
                        )
                );
    }
}
