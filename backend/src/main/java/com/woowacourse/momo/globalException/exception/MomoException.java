package com.woowacourse.momo.globalException.exception;

import lombok.Getter;

@Getter
public class MomoException extends RuntimeException {

    int statusCode;
    String errorCode;
    String message;

    public MomoException(ErrorCode code) {
        statusCode = code.getStatusCode();
        errorCode = code.getErrorCode();
        message = code.getMessage();
    }

}
