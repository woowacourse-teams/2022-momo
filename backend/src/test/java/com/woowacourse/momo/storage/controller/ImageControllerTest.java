package com.woowacourse.momo.storage.controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StorageService storageService;

    @Test
    void imageUploadTest() throws Exception {
        when(storageService.save(any())).thenReturn("abc.txt");

        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "abc.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "abcdefgh".getBytes()
        );

        this.mockMvc.perform(multipart("/api/file/").file(file))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(header().string("Location", "/api/file/abc.txt"));
    }

    @Test
    void imageLoadTest() throws Exception {
        when(storageService.load("abc.txt")).thenReturn("abcdefgh".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/file/abc.txt"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().bytes("abcdefgh".getBytes()));
    }

}
