package com.woowacourse.momo.storage.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ImageProvider {

    private final String imageServerDomain;
    private final String defaultCategoryPath;
    private final String defaultGroupPath;
    private final String savedGroupPath;

    public ImageProvider(@Value("${image.server.domain}") String imageServerDomain,
                         @Value("${image.saved-image-path.default-category}") String defaultCategoryPath,
                         @Value("${image.saved-image-path.default-group}") String defaultGroupPath,
                         @Value("${image.saved-image-path.saved-group}") String savedGroupPath) {
        this.imageServerDomain = imageServerDomain;
        this.defaultCategoryPath = defaultCategoryPath;
        this.defaultGroupPath = defaultGroupPath;
        this.savedGroupPath = savedGroupPath;
    }

    public String generateGroupImageUrl(String imageName, boolean defaultImage) {
        return imageServerDomain + getGroupPath(defaultImage) + "/" + imageName;
    }

    private String getGroupPath(boolean defaultImage) {
        if (defaultImage) {
            return defaultGroupPath;
        }
        return savedGroupPath;
    }

    public String generateCategoryImageUrl(String imageName) {
        return imageServerDomain + defaultCategoryPath + "/" + imageName;
    }
}
