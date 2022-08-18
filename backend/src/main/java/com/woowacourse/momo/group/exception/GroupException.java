package com.woowacourse.momo.group.exception;

import com.woowacourse.momo.global.exception.exception.CustomMomoException;

public class GroupException extends CustomMomoException {

    public GroupException(GroupExceptionMessage message) {
        super(message);
    }
}
