package com.woowacourse.momo.support.logging;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

import com.woowacourse.momo.support.logging.manager.LogManager;

@RequiredArgsConstructor
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    private final List<LogManager> logManagers;

    public void printExceptionPoint(JoinPoint joinPoint) {
        String logMessage = extractExceptionInfo(joinPoint);
        LOGGER.error(ConsolePrettier.red(logMessage));
        logManagers.forEach(logManager -> logManager.writeMessage(logMessage));
    }

    public void printStackTrace(Exception exception, JoinPoint joinPoint) {
        LOGGER.error(ConsolePrettier.red("" + TraceExtractor.getStackTrace(exception)));
        logManagers.forEach(logManager -> logManager.writeException(exception);
    }

    private String extractExceptionInfo(JoinPoint joinPoint) {
        return getPathAndClassName(joinPoint) + "/" + getMethodName(joinPoint) + "(" + getParams(joinPoint) + ")";
    }

    private String getPathAndClassName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private String getParams(JoinPoint joinPoint) {
        return Arrays.toString(joinPoint.getArgs());
    }
}
