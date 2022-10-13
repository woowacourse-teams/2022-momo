package com.woowacourse.momo.storage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum GroupImageErrorCode implements ErrorCode {

    MEMBER_IS_NOT_HOST(400, "GROUP_IMAGE_001", "모임의 주최자가 아닙니다."),

    MULTIPART_FILE_DOSE_NOT_READ(400, "GROUP_IMAGE_002", "이미지 파일을 읽는 도중에 문제가 발생하였습니다."),

    RESPONSE_IS_4XX(400, "GROUP_IMAGE_003", "이미지 서버에서 4XX 에러가 발생하였습니다."),
    RESPONSE_IS_5XX(400, "GROUP_IMAGE_004", "이미지 서버에서 5XX 에러가 발생하였습니다."),
    RESPONSE_IS_NULL(400, "GROUP_IMAGE_004", "이미지 서버의 응답값이 null 입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
