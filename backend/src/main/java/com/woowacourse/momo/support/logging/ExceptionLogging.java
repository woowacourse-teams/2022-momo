package com.woowacourse.momo.support.logging;

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

    @Pointcut("execution(* com.woowacourse.momo.*.controller.*.*(..))")
    public void allControllerExecution() {
    }

    @Pointcut("@annotation(com.woowacourse.momo.support.logging.UnhandledErrorLogging)")
    public void exceptionMethod() {
    }

    @AfterThrowing(value = "allControllerExecution()", throwing = "exception")
    public void exceptionStackTrace(Exception exception) {
        logging.printStackTrace(exception);
    }

    @Around("exceptionMethod()")
    public Object exceptionMessage(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logging.printExceptionPoint(joinPoint);
        return result;
    }
}
