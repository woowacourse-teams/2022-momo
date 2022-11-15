package com.woowacourse.momo.fixture;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public enum ImageFixture {

    PNG_IMAGE("file", "asdf.png", MediaType.IMAGE_PNG_VALUE, "asdf".getBytes()),
    /**
     * 1,024 * 1,024 B = 1 MB
     */
    LARGE_IMAGE("file", "large.png", MediaType.IMAGE_PNG_VALUE, new byte[1024 * 1024 * 100]),
    ;

    private final String name;
    private final String originalFilename;
    private final String mediaType;
    private final byte[] size;

    ImageFixture(String name, String originalFilename, String mediaType, byte[] size) {
        this.name = name;
        this.originalFilename = originalFilename;
        this.mediaType = mediaType;
        this.size = size;
    }

    public MockMultipartFile toMultipartFile() {
        return new MockMultipartFile(name, originalFilename, mediaType, size);
    }
}
