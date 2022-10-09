package com.woowacourse.momo.member.exception;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_EXIST(400, "MEMBER_001", "멤버가 존재하지 않습니다."),
    MEMBER_DELETED(400, "MEMBER_002", "탈퇴한 멤버입니다."),
    MEMBER_DELETED_EXIST_IN_PROGRESS_GROUP(400, "MEMBER_003", "진행중인 모임이 있어 탈퇴할 수 없습니다."),
    MEMBER_WRONG_PASSWORD(400, "MEMBER_004", "비밀번호가 일치하지 않습니다."),

    MEMBER_NAME_SHOULD_NOT_BE_BLANK(400, "MEMBER_005", "사용자의 이름이 공백입니다."),
    MEMBER_NAME_CANNOT_BE_OUT_OF_RANGE(400, "MEMBER_006", "사용자 이름은 1자 이상 20자 이하여야 합니다."),

    MEMBER_PASSWORD_PATTERN_MUST_BE_VALID(400, "MEMBER_008", "패스워드는 영문자와 하나 이상의 숫자, 특수 문자를 갖고 있어야 합니다."),

    USER_ID_SHOULD_NOT_BE_BLANK(400, "MEMBER_010", "사용자의 아이디가 공백입니다."),
    USER_ID_SHOULD_BE_EMAIL_FORMAT(400, "MEMBER_011", "구글 아이디가 이메일 형식이 아닙니다."),
    MEMBER_INVALID_ID_AND_PASSWORD(400, "MEMBER_012", "아이디나 비밀번호가 다릅니다."),
    USER_ID_CANNOT_BE_OUT_OF_RANGE(400, "MEMBER_013", "사용자 아이디는 4자 이상 50자 이하여야 합니다."),

    SIGNUP_INVALID_ID(400, "SIGNUP_001", "잘못된 형식의 아이디입니다."),
    SIGNUP_INVALID_PASSWORD(400, "SIGNUP_002", "잘못된 형식의 비밀번호입니다."),
    SIGNUP_USER_ID_DUPLICATED(400, "SIGNUP_003", "이미 가입된 아이디입니다."),
    SIGNUP_USER_NAME_DUPLICATED(400, "SIGNUP_004", "이미 가입된 이름입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    MemberErrorCode(int statusCode, String errorCode, String message) {
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
