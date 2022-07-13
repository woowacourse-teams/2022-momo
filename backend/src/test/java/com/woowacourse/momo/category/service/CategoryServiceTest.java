package com.woowacourse.momo.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.category.service.dto.response.CategoryResponse;
import com.woowacourse.momo.category.service.dto.response.CategoryResponseAssembler;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        List<CategoryResponse> expected = Category.getAll()
                .stream()
                .map(CategoryResponseAssembler::categoryResponse)
                .collect(Collectors.toList());

        List<CategoryResponse> actual = categoryService.findAll();

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
}
