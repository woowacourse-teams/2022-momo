package com.woowacourse.momo.storage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum GroupImageErrorCode implements ErrorCode {

    MEMBER_IS_NOT_HOST(400, "GROUP_IMAGE_ERROR_001", "모임의 주최자가 아닙니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
