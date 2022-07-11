package com.woowacourse.momo.group.exception;

public class InvalidCategoryException extends GroupException {

    public InvalidCategoryException() {
        super("존재하지 않는 카테고리입니다.");
    }
}
