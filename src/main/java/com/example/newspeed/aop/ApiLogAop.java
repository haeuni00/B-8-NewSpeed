package com.example.newspeed.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Slf4j(topic = "ApiLogAop")
@Aspect
@Component
public class ApiLogAop {
    @After("execution(* com.example.newspeed.controller.*.*(..))")
    public void ApiLog(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("Request URL : " + request.getRequestURL() + ", HTTP Method : " + request.getMethod());
    }

}
