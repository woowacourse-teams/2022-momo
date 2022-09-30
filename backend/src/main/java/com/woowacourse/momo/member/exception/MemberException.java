package com.woowacourse.momo.member.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class MemberException extends MomoException {

    public MemberException(MemberErrorCode code) {
        super(code);
    }
}
