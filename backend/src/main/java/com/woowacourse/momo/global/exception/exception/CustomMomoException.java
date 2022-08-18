package com.woowacourse.momo.global.exception.exception;

public class CustomMomoException extends RuntimeException {

    protected CustomMomoException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
