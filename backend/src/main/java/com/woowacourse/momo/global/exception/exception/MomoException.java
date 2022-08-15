package com.woowacourse.momo.global.exception.exception;

import lombok.Getter;

@Getter
public class MomoException extends RuntimeException {

    private final int statusCode;
    private final String errorCode;
    private final String message;

    public MomoException(ErrorCode code) {
        statusCode = code.getStatusCode();
        errorCode = code.getErrorCode();
        message = code.getMessage();
    }
}
