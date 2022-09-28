package com.woowacourse.momo.global.exception.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER_ERROR_001", "내부 서버 오류입니다."),


    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_001", "토큰의 유효기간이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_002", "토큰이 유효하지 않습니다."),
    AUTH_REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_003", "로그인이 필요합니다."),
    AUTH_DELETE_NO_HOST(HttpStatus.FORBIDDEN.value(), "AUTH_ERROR_004", "주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다."),

    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 Access 토큰 요청이 실패했습니다."),
    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 Access 토큰에 대하여 응답 Body가 비어 있습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 회원정보 요청이 실패했습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 회원정보에 대하여 응답 Body가 비어 있습니다."),

    SIGNUP_INVALID_ID(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_001", "잘못된 형식의 아이디입니다."),
    SIGNUP_INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_002", "잘못된 형식의 비밀번호입니다."),
    SIGNUP_ALREADY_REGISTER(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_003", "이미 가입된 아이디입니다."),

    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "CATEGORY_ERROR_001", "존재하지 않는 카테고리입니다."),

    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST.value(), "FILE_ERROR_001", "저장할 수 없는 확장자입니다."),
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FILE_ERROR_002", "파일 입출력 에러입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
