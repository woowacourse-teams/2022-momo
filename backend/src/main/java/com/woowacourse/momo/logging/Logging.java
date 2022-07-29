package com.woowacourse.momo.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
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

    @Pointcut("execution(* " + BASE_PATH + "." + EXCEPTION_PACKAGE + "..*.*(..))")
    protected void predictedExceptionMethod() {
    }

    @Pointcut("execution(* " + BASE_PATH + "." + EXCEPTION_PACKAGE + "." + GLOBAL_EXCEPTION_METHOD + "(..))")
    protected void exceptionMethod() {
    }

    protected void info(ProceedingJoinPoint joinPoint, Object result) {
        LOGGER.info(log(joinPoint, result));
    }

    protected void debug(ProceedingJoinPoint joinPoint, Object result) {
        LOGGER.debug(log(joinPoint, result));
    }

    protected void trace(ProceedingJoinPoint joinPoint, Object result) {
        LOGGER.trace(log(joinPoint, result));
    }

    protected void warn(ProceedingJoinPoint joinPoint, Object result) {
        LOGGER.warn(ConsolePrettier.yellow("" + getException(joinPoint)));
    }

    protected void error(ProceedingJoinPoint joinPoint, Object result) {
        LOGGER.error(ConsolePrettier.red("" + getException(joinPoint)));
        LogFileManager.write(getException(joinPoint));
    }

    private String log(ProceedingJoinPoint joinPoint, Object result) {
        return ConsolePrettier.green(
                getPathAndClassName(joinPoint) + "/" + getMethodName(joinPoint) + "(" + getParams(joinPoint) + ")"
        );
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

    private Exception getException(ProceedingJoinPoint joinPoint) {
        return (Exception) joinPoint.getArgs()[0];
    }
}
