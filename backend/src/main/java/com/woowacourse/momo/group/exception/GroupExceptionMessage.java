package com.woowacourse.momo.group.exception;

import com.woowacourse.momo.global.exception.exception.ExceptionMessage;

public enum GroupExceptionMessage implements ExceptionMessage {

    GROUP_DOES_NOT_EXIST("존재하지 않는 모임입니다."),


    GROUP_UPDATE_FAILED_BY_CLOSED_EARLY("조기 마감된 모임은 수정할 수 없습니다."),
    GROUP_UPDATE_FAILED_BY_DEADLINE_OVER("마감기한이 지난 모임은 수정할 수 없습니다."),
    GROUP_UPDATE_FAILED_BY_PARTICIPANT_EXIST("참여자가 존재하는 모임은 수정할 수 없습니다."),

    GROUP_DELETE_FAILED_BY_CLOSED_EARLY("조기 마감된 모임은 삭제할 수 없습니다."),
    GROUP_DELETE_FAILED_BY_DEADLINE_OVER("마감기한이 지난 모임은 삭제할 수 없습니다."),
    GROUP_DELETE_FAILED_BY_PARTICIPANT_EXIST("참여자가 존재하는 모임은 삭제할 수 없습니다."),

    GROUP_ALREADY_CLOSED_EARLY("해당 모임은 이미 조기 모감되었습니다."),
    GROUP_ALREADY_DEADLINE_OVER("해당 모임은 이미 마감기한이 지났습니다."),




    MEMBER_CAPACITY_CANNOT_BE_OUT_OF_RANGE("모임 내 인원은 1명 이상 99명 이하여야 합니다."),

    NAME_CANNOT_BE_BLANK("모임의 이름은 빈 값이 될 수 없습니다."),

    DEADLINE_MUST_BE_SET_FROM_NOW_ON("마감시간이 과거일 수 없습니다."),

    DURATION_START_DATE_MUST_BE_BEFORE_END_DATE("기간의 시작일은 종료일 이전이어야 합니다."),
    DURATION_MUST_BE_SET_FROM_NOW_ON("시작일과 종료일은 과거일 수 없습니다."),
    DURATION_MUST_BE_SET_BEFORE_DEADLINE("마감시간이 시작 일자 이후일 수 없습니다."),

    SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME("일정의 시작 시간은 종료 시간 이전이어야 합니다."),
    SCHEDULE_MUST_BE_INCLUDED_IN_DURATION("일정은 모임 기간에 포함되어야 합니다."),


    HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP("주최자는 자신의 모임에 참여할 수 없습니다."),
    MEMBER_IS_NOT_PARTICIPANT("모임의 참여자가 아닙니다."),
    MEMBER_IS_ALREADY_PARTICIPANT("회원은 이미 해당 모임의 참여자입니다."),
    CAPACITY_ALREADY_FULL("참여인원이 가득 찼습니다."),
    CAPACITY_CANNOT_BE_LESS_THAN_PARTICIPANTS_SIZE("참가인원제한은 현재 참가자의 인원수보다 적을 수 없습니다."),

    MEMBER_CANNOT_PARTICIPATE_IN_CLOSED_GROUP("마감된 모임에는 참여할 수 없습니다."),
    HOST_CANNOT_LEAVE_OWN_GROUP("주최자는 모임에 탈퇴할 수 없습니다."),
    PARTICIPANT_LEAVE_DEADLINE("모집이 마감된 모임입니다."),
    GROUP_ALREADY_CLOSED("조기종료된 모임입니다."),

    ;



    private final String message;

    GroupExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
