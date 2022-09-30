package com.woowacourse.momo.storage.exception;

import com.woowacourse.momo.global.exception.exception.MomoException;

public class StorageException extends MomoException {

    public StorageException(StorageErrorCode code) {
        super(code);
    }
}
