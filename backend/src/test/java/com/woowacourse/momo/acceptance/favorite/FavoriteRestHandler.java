package com.woowacourse.momo.acceptance.favorite;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class FavoriteRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/groups";
    private static final String RESOURCE = "/like";

    public static ValidatableResponse 모임을_찜한다(String accessToken, Long groupId) {
        return postRequest(accessToken, BASE_URL + "/" + groupId + RESOURCE);
    }

    public static ValidatableResponse 모임을_찜을_취소한다(String accessToken, Long groupId) {
        return deleteRequest(accessToken, BASE_URL + "/" + groupId + RESOURCE);
    }
}
