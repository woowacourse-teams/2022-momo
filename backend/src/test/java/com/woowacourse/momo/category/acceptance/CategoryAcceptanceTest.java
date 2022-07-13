package com.woowacourse.momo.category.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.category.service.dto.response.CategoryResponse;
import com.woowacourse.momo.category.service.dto.response.CategoryResponseAssembler;
import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;

@SuppressWarnings("NonAsciiCharacters")
class CategoryAcceptanceTest extends AcceptanceTest {

    private static final String BASE_URL = "/api/categories";

    @Test
    void 카테고리_목록_조회() {
        ExtractableResponse<Response> response = RestAssuredConvenienceMethod.getRequest2(BASE_URL);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<CategoryResponse> expected = Category.getAll()
                .stream()
                .map(CategoryResponseAssembler::categoryResponse)
                .collect(Collectors.toList());

        List<CategoryResponse> actual = List.of(response.as(CategoryResponse[].class));
        assertThat(actual).usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(expected);
    }
}
