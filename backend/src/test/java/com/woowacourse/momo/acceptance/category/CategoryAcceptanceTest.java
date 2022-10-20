package com.woowacourse.momo.acceptance.category;

import static com.woowacourse.momo.acceptance.category.CategoryRestHandler.카테고리_목록을_조회한다;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.woowacourse.momo.acceptance.AcceptanceTest;

@SuppressWarnings("NonAsciiCharacters")
class CategoryAcceptanceTest extends AcceptanceTest {

    @DisplayName("카테고리 목록을 조회하다")
    @Test
    void findCategories() {
        카테고리_목록을_조회한다()
                .statusCode(HttpStatus.OK.value());
    }
}
