package com.woowacourse.momo.global.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Aspect
public class ExceptionLogging {

    private final Logging logging;

    @Pointcut("execution(* com.woowacourse.momo..*.*(..))")
    public void allMethod() {
    }

    @Pointcut("@annotation(UnhandledErrorLogging)")
    public void exceptionMethod() {
    }

    @AfterThrowing(value = "allMethod()", throwing = "exception")
    public void exceptionStackTrace(JoinPoint joinPoint, Exception exception) {
        logging.printExceptionStackTrace(joinPoint);
    }

    @Around("exceptionMethod()")
    public Object exceptionMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logging.printExceptionMessage(joinPoint);
        return result;
    }
}
