package com.woowacourse.momo.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiLogging extends Logging {

    @Around("predictedExceptionMethod()")
    public Object warnLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        warn(joinPoint, result);
        return result;
    }

    @Around("exceptionMethod()")
    public Object errorLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        error(joinPoint, result);
        return result;
    }
}
