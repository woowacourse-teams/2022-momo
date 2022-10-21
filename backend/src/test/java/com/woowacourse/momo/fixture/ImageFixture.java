package com.woowacourse.momo.fixture;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public enum ImageFixture {

    PNG_IMAGE("file", "asdf.png", MediaType.IMAGE_PNG_VALUE, "asdf".getBytes())
    ;

    private final String name;
    private final String originalFilename;
    private final String mediaType;
    private final byte[] content;

    ImageFixture(String name, String originalFilename, String mediaType, byte[] content) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.mediaType = mediaType;
        this.content = content;
    }

    public MockMultipartFile toMultipartFile() {
        return new MockMultipartFile(name, originalFilename, mediaType, content);
    }
}
