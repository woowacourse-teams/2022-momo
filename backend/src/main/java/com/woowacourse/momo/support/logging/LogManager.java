package com.woowacourse.momo.support.logging;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LogManager {

    void writeMessage(String message);

    void writeException(Exception exception);
}
