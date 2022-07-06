package com.woowacourse.momo.acceptance;

import com.woowacourse.momo.service.dto.CategoryResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("classpath:init.sql")
class CategoryAcceptanceTest extends AcceptanceTest {

    @Test
    void 카테고리_목록_조회() {
        Response searchResponse = RestAssured.given()
                .log().all()
                .when().get("/api/categories");

        searchResponse.then()
                .statusCode(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        List<CategoryResponse> categoryResponses = List.of(searchResponse.as(CategoryResponse[].class));

        assertThat(categoryResponses).hasSize(5);
    }
}
