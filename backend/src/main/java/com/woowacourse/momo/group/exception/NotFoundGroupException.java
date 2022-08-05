package com.woowacourse.momo.group.exception;

public class NotFoundGroupException extends GroupException {

    public NotFoundGroupException() {
        super("GROUP_ERROR_001"); // 존재하지 않는 그룹입니다.
    }
}
