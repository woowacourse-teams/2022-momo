package com.woowacourse.momo.group.exception;

public class NotFoundGroupException extends GroupException {

    public NotFoundGroupException() {
        super("존재하지 않는 모임입니다.");
    }
}
