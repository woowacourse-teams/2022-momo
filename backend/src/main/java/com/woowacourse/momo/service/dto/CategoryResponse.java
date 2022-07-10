package com.woowacourse.momo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.domain.category.Category;

@Getter
@AllArgsConstructor
public class CategoryResponse {

    private final long id;
    private final String name;

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
