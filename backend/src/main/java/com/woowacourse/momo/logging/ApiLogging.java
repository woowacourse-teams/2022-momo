package com.woowacourse.momo.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiLogging extends Logging {

    @AfterThrowing(value = "allMethod()", throwing = "exception")
    public void exceptionStackTrace(JoinPoint joinPoint, Exception exception) {
        printExceptionStackTrace(joinPoint);
    }

    @Around("exceptionMethod()")
    public Object exceptionMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        printExceptionMessage(joinPoint);
        return result;
    }
}
