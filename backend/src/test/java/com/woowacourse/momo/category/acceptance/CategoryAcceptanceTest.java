package com.woowacourse.momo.category.acceptance;

import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.woowacourse.momo.common.acceptance.AcceptanceTest;
import com.woowacourse.momo.common.acceptance.RestAssuredConvenienceMethod;

@SuppressWarnings("NonAsciiCharacters")
class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 카테고리_목록_조회() {
        RestAssuredConvenienceMethod.getRequest("/api/categories")
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("", Matchers.hasSize(9))
                .body("[0].id", is(1)).body("[0].name", is("스터디"))
                .body("[1].id", is(2)).body("[1].name", is("모각코"))
                .body("[2].id", is(3)).body("[2].name", is("식사"))
                .body("[3].id", is(4)).body("[3].name", is("카페"))
                .body("[4].id", is(5)).body("[4].name", is("술"))
                .body("[5].id", is(6)).body("[5].name", is("운동"))
                .body("[6].id", is(7)).body("[6].name", is("게임"))
                .body("[7].id", is(8)).body("[7].name", is("여행"))
                .body("[8].id", is(9)).body("[8].name", is("기타"));
    }
}
