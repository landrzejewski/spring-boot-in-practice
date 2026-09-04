package pl.fullstackdeveloper.payments.adapters.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pl.fullstackdeveloper.common.annotations.Atomic;

import static pl.fullstackdeveloper.payments.adapters.common.aop.Aop.findAnnotation;

@Aspect
@Component
public final class AtomicAspect {

    private final PlatformTransactionManager platformTransactionManager;

    public AtomicAspect(final PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Around("@annotation(pl.fullstackdeveloper.common.annotations.Atomic) || within(@pl.fullstackdeveloper.common.annotations.Atomic *)")
    public Object runWithTransaction(final ProceedingJoinPoint joinPoint) throws Throwable {
        var annotation = findAnnotation(joinPoint, Atomic.class);
        var transactionDefinition = transactionDefinition(annotation);
        var transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            var result = joinPoint.proceed();
            platformTransactionManager.commit(transactionStatus);
            return result;
        } catch (Throwable throwable) {
            transactionStatus.setRollbackOnly();
            throw throwable;
        }
    }

    private TransactionDefinition transactionDefinition(final Atomic atomic) {
        var transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(atomic.timeoutInMilliseconds());
        return transactionDefinition;
    }

}
