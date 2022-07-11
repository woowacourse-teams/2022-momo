package com.woowacourse.momo.category.service.dto.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.woowacourse.momo.category.domain.category.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponseAssembler {

    public static CategoryResponse categoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
