package com.woowacourse.momo.support.logging.manager;

public class LogManagerAdapter {

    public static void writeMessage(LogManager manager, String message) {
        if (!manager.isUsed()) {
            return;
        }

        manager.writeMessage(message);
    }

    public static void writeException(LogManager manager, Exception exception) {
        if (!manager.isUsed()) {
            return;
        }

        manager.writeException(exception);
    }
}
