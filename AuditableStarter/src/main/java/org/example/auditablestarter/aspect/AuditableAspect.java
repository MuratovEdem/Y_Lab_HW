package org.example.auditablestarter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
public class AuditableAspect {
    private final Logger logger = Logger.getLogger(AuditableAspect.class.getName());

    @Before("@within(org.example.auditablestarter.annotations.Auditable) && execution(* *(..))")
    public void beforeAudit(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.info("Начало выполнения метода - " + joinPoint.getSignature().getName() +
                    ", класса - " + joinPoint.getSourceLocation().getWithinType().getName() +
                    ", с входящими данными - " + Arrays.toString(args));
        } else {
            logger.info("Начало выполнения метода - " + joinPoint.getSignature().getName() +
                    ", класса - " + joinPoint.getSourceLocation().getWithinType().getName());
        }
    }

    @AfterReturning(value = "@within(org.example.auditablestarter.annotations.Auditable) && execution(* *(..))", returning = "returningValue")
    public void afterReturning(JoinPoint joinPoint, Object returningValue) {
        if (returningValue != null) {
            logger.info("Успешно выполнен метод - " + joinPoint.getSignature().getName() +
                            ", класса - " + joinPoint.getSourceLocation().getWithinType().getName() +
                            ", с результатом выполнения - " + returningValue);
        } else {
            logger.info("Успешно выполнен метод - " + joinPoint.getSignature().getName() +
                            ", класса - " + joinPoint.getSourceLocation().getWithinType().getName());
        }
    }

    @AfterThrowing(value = "@within(org.example.auditablestarter.annotations.Auditable) && execution(* *(..))", throwing = "exception")
    public void recordFailedExecution(JoinPoint joinPoint, Exception exception) {
        logger.info("Метод - " + joinPoint.getSignature().getName() +
                ", класса - " + joinPoint.getSourceLocation().getWithinType().getName() + ", выбросил исключение - " + exception);
    }
}
