package com.woowacourse.momo.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final String BASE_PATH = "com.woowacourse.momo";
    private static final String EXCEPTION_PACKAGE = "globalException";
    private static final String GLOBAL_EXCEPTION_METHOD = "ControllerAdvice.handleException";

    @Pointcut("execution(* " + BASE_PATH + "..*.*(..))")
    protected void allMethod() {
    }

    @Pointcut("execution(* " + BASE_PATH + "." + EXCEPTION_PACKAGE + "..*.*(..))")
    protected void predictedExceptionMethod() {
    }

    @Pointcut("execution(* " + BASE_PATH + "." + EXCEPTION_PACKAGE + "." + GLOBAL_EXCEPTION_METHOD + "(..))")
    protected void exceptionMethod() {
    }

    protected void info(String prefix, ProceedingJoinPoint joinPoint) {
        LOGGER.info(log(prefix, joinPoint));
    }

    protected void debug(String prefix, ProceedingJoinPoint joinPoint) {
        LOGGER.debug(log(prefix, joinPoint));
    }

    protected void trace(String prefix, ProceedingJoinPoint joinPoint) {
        LOGGER.trace(log(prefix, joinPoint));
    }

    protected void warn(String prefix, ProceedingJoinPoint joinPoint) {
        LOGGER.warn(log(prefix, joinPoint));
    }

    protected void error(String prefix, ProceedingJoinPoint joinPoint) {
        LOGGER.error(exceptionLog(prefix, joinPoint));
    }

    private String log(String prefix, ProceedingJoinPoint joinPoint) {
        return ANSI_YELLOW + prefix + getPathAndClassName(joinPoint) + "/" + getMethodName(joinPoint)
                 + "(" + getParams(joinPoint) + ")" + ANSI_RESET;
    }

    private String exceptionLog(String prefix, ProceedingJoinPoint joinPoint) {
        return ANSI_RED + prefix + getPathAndClassName(joinPoint) + "/" + getMethodName(joinPoint)
                + "(" + getParams(joinPoint) + ")" + ANSI_RESET;
    }

    private String getPathAndClassName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private String getParams(ProceedingJoinPoint joinPoint) {
        return Arrays.toString(joinPoint.getArgs());
    }
}
