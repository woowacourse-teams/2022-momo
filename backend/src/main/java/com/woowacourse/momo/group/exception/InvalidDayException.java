package com.woowacourse.momo.group.exception;

public class InvalidDayException extends GroupException {

    public InvalidDayException() {
        super("존재하지 않는 요일입니다.");
    }
}
