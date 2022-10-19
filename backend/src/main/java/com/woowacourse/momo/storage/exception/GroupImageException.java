package com.woowacourse.momo.storage.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class GroupImageException extends MomoException {

    public GroupImageException(GroupImageErrorCode code) {
        super(code);
    }
}
