package com.woowacourse.momo.auth.exception;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {

    AUTH_EXPIRED_TOKEN(401, "AUTH_001", "토큰의 유효기간이 만료되었습니다."),
    AUTH_INVALID_TOKEN(401, "AUTH_002", "토큰이 유효하지 않습니다."),
    AUTH_REQUIRED_LOGIN(401, "AUTH_003", "로그인이 필요합니다."),
    AUTH_DELETE_NO_HOST(403, "AUTH_004", "주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다."),

    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_2XX_STATUS(500, "OAUTH_001", "구글에 전송한 Access 토큰 요청이 실패했습니다."),
    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_EXIST_BODY(500, "OAUTH_001", "구글에 요청한 Access 토큰에 대하여 응답 Body가 비어 있습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS(500, "OAUTH_001", "구글에 전송한 회원정보 요청이 실패했습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY(500, "OAUTH_001", "구글에 요청한 회원정보에 대하여 응답 Body가 비어 있습니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    AuthErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
