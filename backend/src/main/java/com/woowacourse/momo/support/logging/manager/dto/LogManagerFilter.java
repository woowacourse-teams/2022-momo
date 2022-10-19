package com.woowacourse.momo.support.logging.manager.dto;

import com.woowacourse.momo.support.logging.manager.LogManager;

public class LogManagerFilter implements LogManager {

    private final LogManager logManager;

    public LogManagerFilter(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void writeMessage(String message) {
        logManager.writeMessage(message);
    }

    @Override
    public void writeException(Exception exception) {

    }
}
