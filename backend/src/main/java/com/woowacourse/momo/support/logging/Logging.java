package com.woowacourse.momo.support.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    private final LogManager logManager;

    public void printExceptionPoint(JoinPoint joinPoint) {
        String logMessage = extractExceptionInfo(joinPoint);
        LOGGER.error(ConsolePrettier.red(logMessage));
        logManager.writeMessage(logMessage);
    }

    public void printStackTrace(Exception exception, JoinPoint joinPoint) {
        LOGGER.error(ConsolePrettier.red("" + TraceExtractor.getStackTrace(exception)));
        logManager.writeException(exception);
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
