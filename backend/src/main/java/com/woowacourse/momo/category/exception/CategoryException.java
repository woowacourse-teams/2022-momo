package com.woowacourse.momo.category.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class CategoryException extends MomoException {

    public CategoryException(CategoryErrorCode code) {
        super(code);
    }
}
