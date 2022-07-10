package com.woowacourse.momo.group.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.group.domain.Category;

@Getter
@AllArgsConstructor
public class CategoryResponse {

    private final long id;
    private final String name;

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
