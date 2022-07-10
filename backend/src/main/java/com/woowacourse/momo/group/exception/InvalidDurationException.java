package com.woowacourse.momo.group.exception;

public class InvalidDurationException extends GroupException {

    public InvalidDurationException() {
        super("시작일은 종료일 이후가 될 수 없습니다.");
    }
}
