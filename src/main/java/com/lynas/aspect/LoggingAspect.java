package com.lynas.aspect;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sazzad on 6/29/16
 */
@Component
@Aspect
public class LoggingAspect {

    private final Gson gson;

    @Autowired
    public LoggingAspect(Gson gson) {
        this.gson = gson;
    }

    @Pointcut("execution(public * respOK(..))")
    public void logResponse(){}

    @Before("logResponse()")
    public void loggingAdvice(JoinPoint joinPoint) {
        System.out.println(gson.toJson(joinPoint.getArgs()[0]));
    }
}
