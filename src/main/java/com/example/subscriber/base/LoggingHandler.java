package com.example.subscriber.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;


/**
 * @author mali.sahin
 * @since 7/10/19.
 */
@Aspect
@Component
public class LoggingHandler extends AbstractBaseComponent {

    @Pointcut("within(com.example.subscriber.controller.*)")
    public void controller() {
    }

    @Pointcut("execution(* com.example.subscriber.endpoint..*(..))")
    public void endpoint() {

    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    @Before("execution(* com.example.subscriber.controller..*(..)) || execution(* com.example.subscriber.endpoint..*(..))")
    public void logBefore(JoinPoint joinPoint) {

        final StringBuilder builder = new StringBuilder();
        builder.append(dateTimeFormatter.format(LocalDateTime.now()));
        builder.append(" ");
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        builder.append(request.getRequestURI());
        builder.append("[");
        builder.append(request.getMethod());
        builder.append("]");
        builder.append(getBody(joinPoint));
        logger.info(builder.toString());
    }

    private String getBody(JoinPoint joinPoint) {
        final StringBuilder builder = new StringBuilder();
        final Optional<Object> requestBody = Optional.of(joinPoint.getArgs())
                .filter(args -> args.length > 0)
                .map(args -> args[0]);
        try {
            builder.append(" [ ");
            if (requestBody.isPresent()) {
                final Class<?> thisClass = Class.forName(requestBody.get().getClass().getName());
                for (Field field : thisClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    builder.append(field.getName())
                            .append(" = ")
                            .append(field.get(requestBody.get()))
                            .append(", ");
                }
            }
            builder.append("]");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return builder.toString();
    }

    @AfterThrowing(pointcut = "execution(* com.example.subscriber.controller..*(..)) || execution(* com.example.subscriber.endpoint..*(..))",
            throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        final StringBuilder builder = new StringBuilder();
        builder.append(dateTimeFormatter.format(LocalDateTime.now()));
        builder.append("An exception has been thrown in ");
        builder.append(joinPoint.getSignature().getName()).append("()");
        builder.append("Error Message: ");
        builder.append(exception.getMessage());
        logger.error(builder.toString());
        exception.printStackTrace();
    }

}
