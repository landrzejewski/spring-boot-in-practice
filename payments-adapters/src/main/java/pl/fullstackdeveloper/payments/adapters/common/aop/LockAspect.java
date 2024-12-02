package pl.fullstackdeveloper.payments.adapters.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.common.annotations.Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static pl.fullstackdeveloper.common.annotations.Lock.LockType.WRITE;

@Aspect
@Component
public final class LockAspect {

    @Around("@annotation(lock)")
    public Object lock(final ProceedingJoinPoint joinPoint, final Lock lock) throws Throwable {
        var readWriteLock = new ReentrantReadWriteLock();
        var targetLock = lock.value() == WRITE ? readWriteLock.writeLock() : readWriteLock.readLock();
        targetLock.lock();
        try {
            return joinPoint.proceed();
        } finally {
            targetLock.unlock();
        }
    }

}
