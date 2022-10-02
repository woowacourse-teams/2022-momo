package com.woowacourse.momo.auth.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class AuthException extends MomoException {

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
