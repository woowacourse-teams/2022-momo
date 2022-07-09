package com.woowacourse.momo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.woowacourse.momo.domain.category.Category;
import com.woowacourse.momo.repository.CategoryRepository;
import com.woowacourse.momo.service.dto.CategoryResponse;

@SpringBootTest
class CategoryServiceTest {

    private static final Category CATEGORY_STUDY = new Category("스터디");
    private static final Category CATEGORY_TRAVEL = new Category("여행");

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.save(CATEGORY_STUDY);
        categoryRepository.save(CATEGORY_TRAVEL);
    }

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        List<CategoryResponse> actual = categoryService.findAll();
        CategoryResponse categoryStudyResponse = CategoryResponse.from(CATEGORY_STUDY);
        CategoryResponse categoryTravelResponse = CategoryResponse.from(CATEGORY_TRAVEL);

        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                .contains(categoryStudyResponse, categoryTravelResponse);
    }
}
