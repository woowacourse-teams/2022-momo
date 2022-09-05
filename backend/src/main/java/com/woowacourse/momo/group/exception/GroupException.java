package com.woowacourse.momo.group.exception;

import com.woowacourse.momo.global.exception.exception.CustomMomoException;

public class GroupException extends CustomMomoException {

    private final GroupExceptionMessage message;

    public GroupException(GroupExceptionMessage message) {
        super(message);
        this.message = message;
    }

    public GroupExceptionMessage getExceptionMessage() {
        return message;
    }
}
