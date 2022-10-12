package com.woowacourse.momo.acceptance.group;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;
import com.woowacourse.momo.category.domain.Category;
import com.woowacourse.momo.fixture.GroupFixture;
import com.woowacourse.momo.group.controller.dto.request.GroupApiRequest;

@SuppressWarnings("NonAsciiCharacters")
public class GroupRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/groups";

    public static ValidatableResponse 모임을_생성한다(String accessToken, GroupFixture group) {
        GroupApiRequest request = group.toApiRequest();
        return postRequest(accessToken, request, BASE_URL);
    }

    public static ValidatableResponse 모임을_생성한다(GroupFixture group) {
        GroupApiRequest request = group.toApiRequest();
        return postRequest(request, BASE_URL);
    }

    public static ValidatableResponse 모임을_조회한다(String accessToken, Long groupId) {
        return getRequest(accessToken, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_조회한다(Long groupId) {
        return getRequest(BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_수정한다(String accessToken, Long groupId, GroupFixture group) {
        GroupApiRequest request = group.toApiRequest();
        return putRequest(accessToken, request, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 본인이_참여한_모임을_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL + "/me/participated");
    }

    public static ValidatableResponse 본인이_주최한_모임을_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL + "/me/hosted");
    }
    public static ValidatableResponse 본인이_찜한_모임을_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL + "/me/liked");
    }

    public static ValidatableResponse 페이지로_모임목록을_조회한다(int pageNumber) {
        return getRequest(BASE_URL + "?page=" + pageNumber);
    }

    public static ValidatableResponse 페이지로_모임목록을_조회한다() {
        return getRequest(BASE_URL + "?page=0");
    }

    public static ValidatableResponse 카테고리별_모임목록을_조회한다(Category category, int firstPageNumber) {
        return getRequest(BASE_URL + "?category=" + category.getId() + "&page=" + firstPageNumber);
    }

    public static ValidatableResponse 키워드로_모임목록을_조회한다(String keyword, int firstPageNumber) {
        return getRequest(BASE_URL + "?keyword=" + keyword + "&page=" + firstPageNumber);
    }

    public static ValidatableResponse 모임을_수정한다(Long groupId, GroupFixture group) {
        GroupApiRequest request = group.toApiRequest();
        return putRequest(request, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_조기마감한다(String accessToken, Long groupId) {
        return postRequestWithNoBody(accessToken, BASE_URL + "/" + groupId + "/close");
    }

    public static ValidatableResponse 모임을_삭제한다(String accessToken, Long groupId) {
        return deleteRequest(accessToken, BASE_URL + "/" + groupId);
    }

    public static ValidatableResponse 모임을_삭제한다(Long groupId) {
        return deleteRequest(BASE_URL + "/" + groupId);
    }
}
