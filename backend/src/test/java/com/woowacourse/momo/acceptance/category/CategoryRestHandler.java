package com.woowacourse.momo.acceptance.category;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/categories";

    public static ValidatableResponse 카테고리_목록을_조회한다() {
        return getRequest(BASE_URL);
    }
}
