package com.woowacourse.momo.global.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    private final LogFileManager logFileManager;

    public void printExceptionStackTrace(JoinPoint joinPoint) {
        String logMessage = log(joinPoint);
        LOGGER.error(ConsolePrettier.red(logMessage));
        logFileManager.writeExceptionStackTrace(logMessage);
    }

    public void printExceptionMessage(JoinPoint joinPoint) {
        LOGGER.error(ConsolePrettier.red("" + getException(joinPoint)));
        logFileManager.writeExceptionMessage(getException(joinPoint));
    }

    private String log(JoinPoint joinPoint) {
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

    private Exception getException(JoinPoint joinPoint) {
        return (Exception) joinPoint.getArgs()[0];
    }
}
