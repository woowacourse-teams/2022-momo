package com.woowacourse.momo.global.exception.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST.value(), "VALIDATION_ERROR_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER_ERROR_001", "내부 서버 오류입니다."),

    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_001", "멤버가 존재하지 않습니다."),
    MEMBER_DELETED(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_002", "탈퇴한 멤버입니다."),
    MEMBER_DELETED_EXIST_IN_PROGRESS_GROUP(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_003", "진행중인 모임이 있어 탈퇴할 수 없습니다."),
    MEMBER_WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_004", "비밀번호가 일치하지 않습니다."),
    MEMBER_NAME_SHOULD_NOT_BE_BLANK(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_005", "사용자의 이름이 빈 값입니다."),
    MEMBER_NAME_MUST_BE_VALID(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_006", "사용자의 이름이 30자를 넘습니다."),
    MEMBER_PASSWORD_SHOULD_NOT_BE_BLANK(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_007", "패스워드가 빈 값입니다."),
    MEMBER_PASSWORD_PATTERN_MUST_BE_VALID(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_008", "패스워드는 영문자와 하나 이상의 숫자, 특수 문자를 갖고 있어야 합니다."),
    MEMBER_ENCRYPTED_PASSWORD_MUST_NOT_UPDATE(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_009", "암호화된 패스워드는 수정할 수 없습니다."),
    MEMBER_ID_SHOULD_NOT_BE_BLANK(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_010", "사용자의 아이디가 빈 값입니다."),
    GOOGLE_ID_SHOULD_BE_IN_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), "MEMBER_ERROR_011", "구글 아이디가 이메일 형식이 아닙니다."),

    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_001", "토큰의 유효기간이 만료되었습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_002", "토큰이 유효하지 않습니다."),
    AUTH_REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED.value(), "AUTH_ERROR_003", "로그인이 필요합니다."),
    AUTH_DELETE_NO_HOST(HttpStatus.FORBIDDEN.value(), "AUTH_ERROR_004", "주최자가 아닌 사람은 모임을 수정하거나 삭제할 수 없습니다."),

    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 Access 토큰 요청이 실패했습니다."),
    OAUTH_ACCESS_TOKEN_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 Access 토큰에 대하여 응답 Body가 비어 있습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_2XX_STATUS(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 전송한 회원정보 요청이 실패했습니다."),
    OAUTH_USERINFO_REQUEST_FAILED_BY_NON_EXIST_BODY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "OAUTH_ERROR_001", "구글에 요청한 회원정보에 대하여 응답 Body가 비어 있습니다."),

    SIGNUP_INVALID_ID(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_001", "잘못된 형식의 아이디입니다."),
    SIGNUP_INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_002", "잘못된 형식의 비밀번호입니다."),
    SIGNUP_ALREADY_REGISTER(HttpStatus.BAD_REQUEST.value(), "SIGNUP_ERROR_003", "이미 가입된 아이디입니다."),

    LOGIN_INVALID_ID_AND_PASSWORD(HttpStatus.BAD_REQUEST.value(), "LOGIN_ERROR_001", "아이디나 비밀번호가 다릅니다."),

    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "CATEGORY_ERROR_001", "존재하지 않는 카테고리입니다."),

    GROUP_NOT_EXIST(HttpStatus.NOT_FOUND.value(), "GROUP_ERROR_001", "존재하지 않는 모임입니다."),
    GROUP_DURATION_START_AFTER_END(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_002", "기간의 시작일은 종료일 이전이어야 합니다."),
    GROUP_SCHEDULE_START_AFTER_END(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_003", "일정의 시작 시간은 종료 시간 이전이어야 합니다."),
    GROUP_SCHEDULE_NOT_RANGE_DURATION(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_004", "일정이 모임 기간에 포함되어야 합니다."),
    GROUP_DURATION_NOT_PAST(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_005", "시작일과 종료일은 과거일 수 없습니다."),
    GROUP_DURATION_NOT_AFTER_DEADLINE(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_006", "마감시간이 시작 일자 이후일 수 없습니다."),
    GROUP_DEADLINE_NOT_PAST(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_007", "마감시간이 과거일 수 없습니다."),
    GROUP_MEMBERS_NOT_IN_RANGE(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_008", "모임 내 인원은 1명 이상 99명 이하여야 합니다."),
    GROUP_ALREADY_FINISH(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_009", "모집 마감된 모임은 수정 및 삭제할 수 없습니다."),
    GROUP_EXIST_PARTICIPANTS(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_010", "참여자가 존재하는 모임은 수정 및 삭제할 수 없습니다."),
    GROUP_NAME_SHOULD_NOT_BE_BLANK(HttpStatus.BAD_REQUEST.value(), "GROUP_ERROR_011", "모임의 이름이 빈 값입니다."),

    PARTICIPANT_JOIN_BY_HOST(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_001", "주최자는 자신의 모임에 참여할 수 없습니다."),
    PARTICIPANT_RE_PARTICIPATE(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_002", "참여자는 본인이 참여한 모임에 재참여할 수 없습니다."),
    PARTICIPANT_FINISHED(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_003", "마감된 모임에는 참여할 수 없습니다."),
    PARTICIPANT_LEAVE_HOST(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_004", "주최자는 모임에 탈퇴할 수 없습니다."),
    PARTICIPANT_LEAVE_NOT_PARTICIPANT(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_005", "모임의 참여자가 아닙니다."),
    PARTICIPANT_LEAVE_DEADLINE(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_006", "모집이 마감된 모임입니다."),
    PARTICIPANT_LEAVE_EARLY_CLOSED(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_007", "조기종료된 모임입니다."),
    PARTICIPANT_CAPACITY_IS_OVER_SIZE(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_008", "수정하려는 최대 인원이 현재 참가자의 수보다 적습니다."),
    PARTICIPANT_PARTICIPANTS_FULL(HttpStatus.BAD_REQUEST.value(), "PARTICIPANT_ERROR_003", "참여인원이 가득 찼습니다."),

    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST.value(), "FILE_ERROR_001", "저장할 수 없는 확장자입니다."),
    FILE_IO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "FILE_ERROR_002", "파일 입출력 에러입니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}
