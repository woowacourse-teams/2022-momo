package com.woowacourse.momo.group.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import static com.woowacourse.momo.group.exception.GroupExceptionMessage.CAPACITY_ALREADY_FULL;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DEADLINE_MUST_BE_SET_FROM_NOW_ON;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DURATION_MUST_BE_SET_BEFORE_DEADLINE;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DURATION_MUST_BE_SET_FROM_NOW_ON;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.DURATION_START_DATE_MUST_BE_BEFORE_END_DATE;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_ALREADY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_ALREADY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DELETE_FAILED_BY_PARTICIPANT_EXIST;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_DOES_NOT_EXIST;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_CLOSED_EARLY;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_DEADLINE_OVER;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.GROUP_UPDATE_FAILED_BY_PARTICIPANT_EXIST;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_CAPACITY_CANNOT_BE_OUT_OF_RANGE;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_IS_ALREADY_PARTICIPANT;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_IS_NOT_HOST;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.MEMBER_IS_NOT_PARTICIPANT;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.NAME_CANNOT_BE_BLANK;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.SCHEDULE_MUST_BE_INCLUDED_IN_DURATION;
import static com.woowacourse.momo.group.exception.GroupExceptionMessage.SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;

import com.woowacourse.momo.global.exception.exception.ExceptionCode;
import com.woowacourse.momo.global.exception.exception.ExceptionMessage;

public enum GroupExceptionCode implements ExceptionCode {

    GROUP_ERROR_001(NOT_FOUND, GROUP_DOES_NOT_EXIST),
    GROUP_ERROR_002(BAD_REQUEST, DURATION_START_DATE_MUST_BE_BEFORE_END_DATE),
    GROUP_ERROR_003(BAD_REQUEST, SCHEDULE_START_TIME_MUST_BE_BEFORE_END_TIME),
    GROUP_ERROR_004(BAD_REQUEST, SCHEDULE_MUST_BE_INCLUDED_IN_DURATION),
    GROUP_ERROR_005(BAD_REQUEST, DURATION_MUST_BE_SET_FROM_NOW_ON),
    GROUP_ERROR_006(BAD_REQUEST, DURATION_MUST_BE_SET_BEFORE_DEADLINE),
    GROUP_ERROR_007(BAD_REQUEST, DEADLINE_MUST_BE_SET_FROM_NOW_ON),
    GROUP_ERROR_008(BAD_REQUEST, MEMBER_CAPACITY_CANNOT_BE_OUT_OF_RANGE),
    GROUP_ERROR_009(BAD_REQUEST,
            GROUP_UPDATE_FAILED_BY_CLOSED_EARLY,
            GROUP_UPDATE_FAILED_BY_DEADLINE_OVER,
            GROUP_DELETE_FAILED_BY_CLOSED_EARLY,
            GROUP_DELETE_FAILED_BY_DEADLINE_OVER
    ),
    GROUP_ERROR_010(BAD_REQUEST,
            GROUP_UPDATE_FAILED_BY_PARTICIPANT_EXIST,
            GROUP_DELETE_FAILED_BY_PARTICIPANT_EXIST
    ),
    GROUP_ERROR_011(BAD_REQUEST, NAME_CANNOT_BE_BLANK),

    PARTICIPANT_ERROR_001(BAD_REQUEST, HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP),
    PARTICIPANT_ERROR_002(BAD_REQUEST, MEMBER_IS_ALREADY_PARTICIPANT),
    PARTICIPANT_ERROR_003(BAD_REQUEST,
            GROUP_ALREADY_CLOSED_EARLY,
            GROUP_ALREADY_DEADLINE_OVER,
            CAPACITY_ALREADY_FULL
            ),
    PARTICIPANT_ERROR_004(BAD_REQUEST, HOST_CANNOT_PARTICIPATE_OR_LEAVE_OWN_GROUP),
    PARTICIPANT_ERROR_005(BAD_REQUEST, MEMBER_IS_NOT_PARTICIPANT),
    PARTICIPANT_ERROR_006(BAD_REQUEST,
            GROUP_ALREADY_CLOSED_EARLY,
            GROUP_ALREADY_DEADLINE_OVER,
            CAPACITY_ALREADY_FULL
            ),
    PARTICIPANT_ERROR_007(BAD_REQUEST, GROUP_ALREADY_CLOSED_EARLY),
    PARTICIPANT_ERROR_008(BAD_REQUEST, CAPACITY_ALREADY_FULL),

    AUTH_ERROR_004(FORBIDDEN, MEMBER_IS_NOT_HOST),
    ;

    private final HttpStatus status;
    private final List<ExceptionMessage> messages;

    GroupExceptionCode(HttpStatus status, ExceptionMessage... messages) {
        this.status = status;
        this.messages = List.of(messages);
    }

    public static GroupExceptionCode from(GroupException e) {
        return Stream.of(values())
                .filter(code -> code.contains(e.getExceptionMessage()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean contains(ExceptionMessage message) {
        return messages.contains(message);
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return name();
    }
}
