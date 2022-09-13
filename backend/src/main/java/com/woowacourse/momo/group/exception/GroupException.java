package com.woowacourse.momo.group.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class GroupException extends MomoException {

    public GroupException(GroupErrorCode code) {
        super(code);
    }
}
