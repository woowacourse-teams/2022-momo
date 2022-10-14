package com.woowacourse.momo.category.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.category.service.dto.response.CategoryResponse;
import com.woowacourse.momo.category.service.dto.response.CategoryResponseAssembler;
import com.woowacourse.momo.storage.support.ImageProvider;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final ImageProvider imageProvider;

    public List<CategoryResponse> findAll() {
        return Arrays.stream(Category.values())
                .map(category ->
                        CategoryResponseAssembler.categoryResponse(
                                category, imageProvider.generateCategoryImageUrl(category.getIconName())
                        )
                )
                .collect(Collectors.toList());
    }
}
