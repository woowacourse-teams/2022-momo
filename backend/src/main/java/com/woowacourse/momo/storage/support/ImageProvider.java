package com.woowacourse.momo.storage.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ImageProvider {

    private final String defaultGroupPath;
    private final String savedGroupPath;

    public ImageProvider(@Value("${image.saved-image-path.default-group}") String defaultGroupPath,
                         @Value("${image.saved-image-path.saved-group}") String savedGroupPath) {
        this.defaultGroupPath = defaultGroupPath;
        this.savedGroupPath = savedGroupPath;
    }
}
