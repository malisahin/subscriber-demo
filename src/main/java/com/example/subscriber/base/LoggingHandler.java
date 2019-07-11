package com.example.subscriber.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;


/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Aspect
@Component
public class LoggingHandler extends AbstractBaseComponent {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("within(@org.springframework.ws.server.endpoint.annotation.Endpoint *)")
    public void endpoint() {

    }


    @Before("controller() && endpoint() ")
    public void logBefore(JoinPoint joinPoint) {
        final LocalTime localTime = LocalTime.now();
        dateTimeFormatter.format(localTime);

        logger.debug("Entering in Method :  " + joinPoint.getSignature().getName());
        logger.debug("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
        logger.debug("Target class : " + joinPoint.getTarget().getClass().getName());
    }

    @AfterReturning(pointcut = "controller() && endpoint()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String returnValue = this.getValue(result);
        logger.debug("Method Return value : " + returnValue);
    }

    @AfterThrowing(pointcut = "controller() && endpoint() && allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

        logger.error(String.format("An exception has been thrown in %1$s  %2$s", joinPoint.getSignature().getName(), "()"));
        logger.error(String.format("Cause : %s", exception.getCause()));
    }

    @Around("controller() && allMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.debug("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }

    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                System.out.println(result);
                // returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }
}
