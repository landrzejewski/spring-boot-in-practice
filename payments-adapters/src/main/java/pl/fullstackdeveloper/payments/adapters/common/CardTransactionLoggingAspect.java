package pl.fullstackdeveloper.payments.adapters.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.TransactionType;

import java.util.logging.Logger;

@Order(10000)
@Aspect
@Component
public final class CardTransactionLoggingAspect {

    private static final Logger LOGGER = Logger.getLogger(CardTransactionLoggingAspect.class.getName());

    //@Pointcut("@annotation(pl.fullstackdeveloper.common.annotations.EnableLogging)")
    @Pointcut("execution(* pl.fullstackdeveloper.payments.application.AddTransactionService.handle(pl.fullstackdeveloper.payments.domain.CardNumber, pl.fullstackdeveloper.common.Money, pl.fullstackdeveloper.payments.domain.TransactionType))")
    public void transaction() {
    }

    @Before(value = "transaction() && args(cardNumber, value, type)", argNames = "joinPoint,cardNumber,value,type")
    public void before(final JoinPoint joinPoint, final CardNumber cardNumber, final Money value, final TransactionType type) {
        LOGGER.info("Starting transaction for card: " + cardNumber);
    }

    @AfterReturning(value = "transaction()")
    public void onSuccess() {
        LOGGER.info("Transaction completed successfully");
    }

    @AfterThrowing(value = "transaction()", throwing = "runtimeException")
    public void onFailure(final JoinPoint joinPoint, final RuntimeException runtimeException) {
        LOGGER.info("Transaction failed with exception: " + runtimeException.getClass().getSimpleName() +
                " (method: " + joinPoint.getSignature() + ")");
    }

    @After("transaction()")
    public void onComplete() {
        LOGGER.info("Transaction registered");
    }

}
