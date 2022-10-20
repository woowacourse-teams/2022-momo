package com.woowacourse.momo.category.service.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponseAssembler {

    public static CategoryResponse categoryResponse(Category category, String imageUrl) {
        return new CategoryResponse(category.getId(), category.getName(), imageUrl);
    }
}
