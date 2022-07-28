package com.woowacourse.momo.logging;

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
        LOGGER.error(log(prefix, joinPoint));
    }

    private String log(String prefix, ProceedingJoinPoint joinPoint) {
        return prefix + getPathAndClassName(joinPoint) + "/" + getMethodName(joinPoint);
    }

    private String getPathAndClassName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
