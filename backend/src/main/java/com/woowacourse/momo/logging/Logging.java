package com.woowacourse.momo.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    private static final String BASE_PATH = "com.woowacourse.momo";
    private static final String EXCEPTION_PACKAGE = "globalException";
    private static final String GLOBAL_EXCEPTION_METHOD = "ControllerAdvice.handleException";

    @Pointcut("execution(* " + BASE_PATH + "..*.*(..))")
    protected void allMethod() {
    }

    @Pointcut("execution(* " + BASE_PATH + "." + EXCEPTION_PACKAGE + "." + GLOBAL_EXCEPTION_METHOD + "(..))")
    protected void exceptionMethod() {
    }

    protected void printExceptionStackTrace(JoinPoint joinPoint) {
        String logMessage = log(joinPoint);
        LOGGER.error(ConsolePrettier.red(logMessage));
        LogFileManager.writeExceptionStackTrace(logMessage);
    }

    protected void printExceptionMessage(JoinPoint joinPoint) {
        LOGGER.error(ConsolePrettier.red("" + getException(joinPoint)));
        LogFileManager.write(getException(joinPoint));
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
