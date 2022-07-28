package com.woowacourse.momo.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiLogging extends Logging {

    private static final String PREFIX_START = "start - ";
    private static final String PREFIX_FINISHED = "finished - ";

    @Around("allMethod()")
    public Object infoLog(ProceedingJoinPoint joinPoint) throws Throwable {
        info(PREFIX_START, joinPoint);
        Object result = joinPoint.proceed();
        info(PREFIX_FINISHED, joinPoint);
        return result;
    }

    @Around("predictedExceptionMethod()")
    public Object warnLog(ProceedingJoinPoint joinPoint) throws Throwable {
        warn(PREFIX_START, joinPoint);
        Object result = joinPoint.proceed();
        warn(PREFIX_FINISHED, joinPoint);
        return result;
    }

    @Around("exceptionMethod()")
    public Object errorLog(ProceedingJoinPoint joinPoint) throws Throwable {
        error(PREFIX_START, joinPoint);
        Object result = joinPoint.proceed();
        error(PREFIX_FINISHED, joinPoint);
        return result;
    }
}
