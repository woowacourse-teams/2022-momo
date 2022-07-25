package com.woowacourse.momo.acceptance.category;

import static org.assertj.core.api.Assertions.assertThat;

import static com.woowacourse.momo.acceptance.RestHandler.getRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.category.service.dto.response.CategoryResponse;
import com.woowacourse.momo.category.service.dto.response.CategoryResponseAssembler;

class CategoryAcceptanceTest extends AcceptanceTest {

    private static final String BASE_URL = "/api/categories";

    @DisplayName("카테고리 목록을 조회하다")
    @Test
    void findCategories() {
        List<CategoryResponse> actual = getRequest(BASE_URL)
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CategoryResponse.class);

        List<CategoryResponse> expected = Arrays.stream(Category.values())
                .map(CategoryResponseAssembler::categoryResponse)
                .collect(Collectors.toList());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
