package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class LoggingAspect {

    @Around("@within(org.example.annotations.Logging) && execution(* *(..))")
    public Object aroundMethods(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method method = methodSignature.getMethod();
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            System.out.println(target.getClass().getName() + "#" + method.getName() + " duration = " + duration + " ms");
        }
    }
}
