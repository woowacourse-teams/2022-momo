package com.woowacourse.momo.service.dto.response.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.domain.category.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponseAssembler {

    public static CategoryResponse categoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
