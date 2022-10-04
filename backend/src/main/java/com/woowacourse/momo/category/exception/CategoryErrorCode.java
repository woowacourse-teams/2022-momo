package com.woowacourse.momo.category.exception;

import org.springframework.http.HttpStatus;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

public enum CategoryErrorCode implements ErrorCode {

    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "CATEGORY_001", "존재하지 않는 카테고리입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    CategoryErrorCode(int statusCode, String errorCode, String message) {
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
