package com.woowacourse.momo.acceptance.participant;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;

@SuppressWarnings("NonAsciiCharacters")
public class ParticipantRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/groups";
    private static final String RESOURCE = "/participants";

    public static ValidatableResponse 모임에_참여한다(String accessToken, Long groupId) {
        return postRequest(accessToken, BASE_URL + "/" + groupId + RESOURCE);
    }

    public static ValidatableResponse 모임에_참여한다(Long groupId) {
        return postRequest(BASE_URL + "/" + groupId + RESOURCE);
    }

    public static ValidatableResponse 참여목록을_조회한다(String accessToken, Long groupId) {
        return getRequest(accessToken, BASE_URL + "/" + groupId + RESOURCE);
    }

    public static ValidatableResponse 참여목록을_조회한다(Long groupId) {
        return getRequest(BASE_URL + "/" + groupId + RESOURCE);
    }

    public static ValidatableResponse 모임을_탈퇴한다(String accessToken, Long groupId) {
        return deleteRequest(accessToken, BASE_URL + "/" + groupId + RESOURCE);
    }
}
