package org.sopt.global.api.aop;

import jakarta.persistence.OptimisticLockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sopt.global.api.annotation.Retry;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxRetries = retry.maxRetries();
        OptimisticLockException lastException = null;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                return joinPoint.proceed();
            } catch (OptimisticLockException e) {
                lastException = e;
                Thread.sleep(100 * (attempt + 1));
            }
        }

        throw lastException;
    }
}