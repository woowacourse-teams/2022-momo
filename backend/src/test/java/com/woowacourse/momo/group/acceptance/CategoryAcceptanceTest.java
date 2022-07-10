package com.woowacourse.momo.group.acceptance;

import static org.hamcrest.Matchers.is;

import com.woowacourse.momo.acceptance.AcceptanceTest;
import com.woowacourse.momo.acceptance.RestAssuredConvenienceMethod;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SuppressWarnings("NonAsciiCharacters")
@Sql("classpath:init.sql")
class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 카테고리_목록_조회() {
        RestAssuredConvenienceMethod.getRequest("/api/categories")
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("", Matchers.hasSize(5))
                .body("[0].id", is(1)).body("[0].name", is("운동"))
                .body("[1].id", is(2)).body("[1].name", is("스터디"))
                .body("[2].id", is(3)).body("[2].name", is("한 잔"))
                .body("[3].id", is(4)).body("[3].name", is("영화"))
                .body("[4].id", is(5)).body("[4].name", is("모각코"));
    }
}
