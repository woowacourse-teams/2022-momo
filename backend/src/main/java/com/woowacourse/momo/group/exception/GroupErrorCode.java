package com.woowacourse.momo.group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.woowacourse.momo.global.exception.exception.ErrorCode;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements ErrorCode {

    NOT_EXIST(404, "GROUP_001", "존재하지 않는 모임입니다."),

    DEADLINE_MUST_BE_SET_FROM_NOW_ON(400, "GROUP_002", "마감시간이 과거일 수 없습니다."),

    DURATION_START_DATE_MUST_BE_BEFORE_END_DATE(400, "GROUP_003", "기간의 시작일은 종료일 이전이어야 합니다."),
    DURATION_MUST_BE_SET_FROM_NOW_ON(400, "GROUP_004", "시작일과 종료일은 과거일 수 없습니다."),
    DURATION_MUST_BE_SET_BEFORE_DEADLINE(400, "GROUP_005", "마감시간이 시작 일자 이후일 수 없습니다."),
    DURATION_MUST_BE_SET_CONTAIN_SCHEDULE(400, "GROUP_023", "기간은 모든 일정을 포함하는 기간이어야 합니다."),

    SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME(400, "GROUP_006", "일정의 시작 시간은 종료 시간 이전이어야 합니다."),
    SCHEDULE_MUST_BE_INCLUDED_IN_DURATION(400, "GROUP_007", "일정은 모임 기간에 포함되어야 합니다."),

    NAME_CANNOT_BE_BLANK(400, "GROUP_008", "모임의 이름은 공백이 될 수 없습니다."),

    CAPACITY_CANNOT_BE_OUT_OF_RANGE(400, "GROUP_009", "모임 내 인원은 1명 이상 99명 이하여야 합니다."),
    CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE(400, "GROUP_010", "참가인원제한은 현재 참가자의 인원수보다 적을 수 없습니다."),

    ALREADY_CLOSED_EARLY(400, "GROUP_011", "해당 모임은 조기 마감되어 있습니다."),
    ALREADY_DEADLINE_OVER(400, "GROUP_012", "해당 모임은 마감기한이 지났습니다."),
    ALREADY_PARTICIPANTS_SIZE_FULL(400, "GROUP_013", "참여인원이 가득 찼습니다."),

    PARTICIPANT_EXIST(400, "GROUP_014", "해당 모임은 참여자가 존재합니다."),

    MEMBER_IS_HOST(400, "GROUP_015", "해당 모임의 주최자입니다."),
    MEMBER_IS_PARTICIPANT(400, "GROUP_016", "해당 모임의 참여자입니다."),
    MEMBER_IS_NOT_HOST(403, "GROUP_017", "해당 모임의 주최자가 아닙니다."),
    MEMBER_IS_NOT_PARTICIPANT(403, "GROUP_018", "해당 모임의 참여자가 아닙니다."),

    MEMBER_ALREADY_LIKE(400, "GROUP_019", "이미 찜한 모임입니다."),
    MEMBER_NOT_YET_LIKE(403, "GROUP_020", "찜하지 않은 모임입니다."),

    NAME_CANNOT_BE_OUT_OF_RANGE(400, "GROUP_021", "모임의 이름은 1자 이상 50자 이하여야 합니다."),
    DESCRIPTION_CANNOT_BE_OUT_OF_RANGE(400, "GROUP_022", "모임의 설명은 1000자 이하여야 합니다.")
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;
}

