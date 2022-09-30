package com.woowacourse.momo.global.exception.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    VALIDATION_ERROR(400, "VALIDATION_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "SERVER_001", "내부 서버 오류입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
