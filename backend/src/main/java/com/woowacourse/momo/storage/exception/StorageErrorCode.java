package com.woowacourse.momo.storage.exception;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

public enum StorageErrorCode implements ErrorCode {

    FILE_INVALID_EXTENSION(400, "FILE_001", "저장할 수 없는 확장자입니다."),
    FILE_IO_ERROR(500, "FILE_002", "파일 입출력 에러입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    StorageErrorCode(int statusCode, String errorCode, String message) {
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
