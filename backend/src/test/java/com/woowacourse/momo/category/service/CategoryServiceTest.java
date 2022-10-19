package com.woowacourse.momo.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.category.service.dto.response.CategoryResponse;
import com.woowacourse.momo.category.service.dto.response.CategoryResponseAssembler;
import com.woowacourse.momo.storage.support.ImageProvider;

@Transactional
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageProvider imageProvider;

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        List<CategoryResponse> expected = Arrays.stream(Category.values())
                .map(category ->
                        CategoryResponseAssembler.categoryResponse(
                                category, imageProvider.generateCategoryImageUrl(category.getIconName())
                        )
                )
                .collect(Collectors.toList());

        List<CategoryResponse> actual = categoryService.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
}
