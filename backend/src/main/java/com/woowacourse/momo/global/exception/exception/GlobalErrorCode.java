package com.woowacourse.momo.global.exception.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER_ERROR_001", "내부 서버 오류입니다."),

    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "CATEGORY_ERROR_001", "존재하지 않는 카테고리입니다."),

    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST.value(), "FILE_ERROR_001", "저장할 수 없는 확장자입니다."),
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FILE_ERROR_002", "파일 입출력 에러입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
