package com.example.subscriber.base;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


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

  @Pointcut("execution(* *.*(..))")
  protected void allMethod() {
  }

  @Before("controller() && allMethod() &&  args(.., request)")
  public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {

  }

  @AfterThrowing(pointcut = "controller() && endpoint() && allMethod() ", throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

    logger.error(String.format("An exception has been thrown in %1$s  %2$s", joinPoint.getSignature().getName(), "()"));
    logger.error(String.format("Cause : %s", exception.getCause()));
  }
}
