package com.woowacourse.momo.global.exception.exception;

public interface ErrorCode {

    int getStatusCode();

    String getErrorCode();

    String getMessage();
}
