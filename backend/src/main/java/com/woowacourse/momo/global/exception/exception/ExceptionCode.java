package com.woowacourse.momo.global.exception.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    HttpStatus getStatus();
}
