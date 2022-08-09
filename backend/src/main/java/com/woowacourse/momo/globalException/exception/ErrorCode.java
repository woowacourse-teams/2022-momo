package com.woowacourse.momo.globalException.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER_ERROR_001", "내부 서버 오류입니다."),

    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_001", "멤버가 존재하지 않습니다."),

    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_001", "토큰의 유효기간이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_002", "토큰이 유효하지 않습니다."),
    AUTH_REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_003", "로그인이 필요합니다."),
    AUTH_DELETE_NO_HOST(HttpStatus.FORBIDDEN.value(), "AUTH_ERROR_004", "주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다."),
    AUTH_DELETE_NO_PARTICIPANT(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_005", "참여자가 아닌 회원은 모임을 탈퇴할 수 없습니다."),

    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 Access 토큰 요청이 실패했습니다."),
    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 Access 토큰에 대하여 응답 Body가 비어 있습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 회원정보 요청이 실패했습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 회원정보에 대하여 응답 Body가 비어 있습니다."),

    SIGNUP_INVALID_ID(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_001", "잘못된 형식의 아이디입니다."),
    SIGNUP_INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_002", "잘못된 형식의 비밀번호입니다."),
    SIGNUP_ALREADY_REGISTER(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_003", "이미 가입된 아이디입니다."),

    LOGIN_INVALID_ID_AND_PASSWORD(HttpStatus.BAD_REQUEST.value(), "LOGIN_ERROR_001", "아이디나 비밀번호가 다릅니다."),

    GROUP_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_001", "존재하지 않는 모임입니다."),
    GROUP_DURATION_START_AFTER_END(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_002", "기간의 시작일은 종료일 이전이어야 합니다."),
    GROUP_SCHEDULE_START_AFTER_END(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_003", "일정의 시작 시간은 종료 시간 이전이어야 합니다."),
    GROUP_SCHEDULE_NOT_RANGE_DURATION(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_004", "일정이 모임 기간에 포함되어야 합니다."),
    GROUP_DURATION_NOT_PAST(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_005", "시작일과 종료일은 과거일 수 없습니다."),
    GROUP_DURATION_NOT_AFTER_DEADLINE(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_006", "마감시간이 시작 일자 이후일 수 없습니다."),
    GROUP_DEADLINE_NOT_PAST(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_007", "마감시간이 과거일 수 없습니다."),
    GROUP_MEMBERS_NOT_IN_RANGE(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_008", "모임 내 인원은 1명 이상 99명 이하여야 합니다."),
    GROUP_ALREADY_FINISH(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_009", "모집 마감된 모임은 수정 및 삭제할 수 없습니다."),

    PARTICIPANT_JOIN_BY_HOST(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_001", "주최자는 자신의 모임에 참여할 수 없습니다."),
    PARTICIPANT_RE_PARTICIPATE(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_002", "참여자는 본인이 참여한 모임에 재참여할 수 없습니다."),
    PARTICIPANT_FINISHED(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_003", "마감된 모임에는 참여할 수 없습니다.")
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

}
