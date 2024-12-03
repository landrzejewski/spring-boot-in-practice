package pl.fullstackdeveloper.payments.adapters.events;

import org.springframework.context.ApplicationEventPublisher;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.TransactionAdded;
import pl.fullstackdeveloper.payments.application.TransactionEventPublisher;

import java.util.logging.Logger;

@Adapter
final class SpringTransactionEventPublisher implements TransactionEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(SpringTransactionEventPublisher.class.getName());

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringTransactionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(final TransactionAdded transactionAdded) {
        LOGGER.info("New %s transaction on card %s".formatted(transactionAdded.transactionType(), transactionAdded.cardNumber()));
        var transactionConfirmed = new TransactionConfirmed(transactionAdded.cardNumber(), transactionAdded.cardNumber());
        applicationEventPublisher.publishEvent(transactionConfirmed);
    }

}
