package com.woowacourse.momo.support.logging.manager;

public interface LogManager {

    void writeMessage(String message);

    void writeException(Exception exception);

    boolean isNotUsed();
}
