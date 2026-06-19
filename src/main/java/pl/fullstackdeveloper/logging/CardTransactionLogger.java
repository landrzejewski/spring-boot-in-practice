package pl.fullstackdeveloper.logging;

import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.fullstackdeveloper.payments.TransactionAdded;
import java.util.logging.Logger;

@Component
public class CardTransactionLogger {

    private static final Logger LOGGER = Logger.getLogger(CardTransactionLogger.class.getName());

   /* @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)*/

    @ApplicationModuleListener
    public void onEvent(final TransactionAdded transactionAdded) throws InterruptedException {
        LOGGER.info("### New event: " + transactionAdded.toString());
        Thread.sleep(2_000);
        LOGGER.info("### After event: " + transactionAdded.toString());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onError(final TransactionAdded transactionAdded) throws InterruptedException {
        LOGGER.info("### Error: " + transactionAdded.toString());
    }

}
