package com.woowacourse.momo.acceptance.member;

import io.restassured.response.ValidatableResponse;

import com.woowacourse.momo.acceptance.RestHandler;
import com.woowacourse.momo.member.service.dto.request.ChangeNameRequest;
import com.woowacourse.momo.member.service.dto.request.ChangePasswordRequest;

@SuppressWarnings("NonAsciiCharacters")
public class MemberRestHandler extends RestHandler {

    private static final String BASE_URL = "/api/members";

    public static ValidatableResponse 개인정보를_조회한다(String accessToken) {
        return getRequest(accessToken, BASE_URL);
    }

    public static ValidatableResponse 비밀번호를_수정한다(String accessToken, String newPassword, String oldPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(newPassword, oldPassword);
        return patchRequest(accessToken, request, BASE_URL + "/password");
    }

    public static ValidatableResponse 이름을_수정한다(String accessToken, String newName) {
        ChangeNameRequest request = new ChangeNameRequest(newName);
        return patchRequest(accessToken, request, BASE_URL + "/name");
    }

    public static ValidatableResponse 회원탈퇴를_한다(String accessToken) {
        return deleteRequest(accessToken, BASE_URL);
    }
}
