package com.gcu.videogamestorefront.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around(
        "execution(* com.gcu.videogamestorefront.controller..*(..)) || " +
        "execution(* com.gcu.videogamestorefront.service..*(..)) || " +
        "execution(* com.gcu.videogamestorefront.repository..*(..))"
    )
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        Class<?> targetClass = joinPoint.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(targetClass);

        String className = targetClass.getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        logger.info(
            "METHOD_ENTRY | class={} | method={} | status=STARTED",
            className,
            methodName
        );

        try {

            Object result = joinPoint.proceed();

            logger.info(
                "METHOD_EXIT | class={} | method={} | status=SUCCESS",
                className,
                methodName
            );

            return result;

        } catch (Exception ex) {

            logger.error(
                "METHOD_EXCEPTION | class={} | method={} | status=FAILED | error={}",
                className,
                methodName,
                ex.getMessage(),
                ex
            );

            throw ex;
        }
    }
}