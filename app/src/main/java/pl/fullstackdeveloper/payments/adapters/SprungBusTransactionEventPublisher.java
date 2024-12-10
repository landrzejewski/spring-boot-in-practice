package pl.fullstackdeveloper.payments.adapters;

import org.springframework.context.ApplicationEventPublisher;
import pl.fullstackdeveloper.orders.adapters.TransactionConfirmed;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.TransactionAdded;
import pl.fullstackdeveloper.payments.application.TransactionEventPublisher;

import java.util.logging.Logger;

@Adapter
final class SprungBusTransactionEventPublisher implements TransactionEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(SprungBusTransactionEventPublisher.class.getName());

    private final ApplicationEventPublisher eventPublisher;

    public SprungBusTransactionEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(final TransactionAdded transactionAdded) {
        LOGGER.info("New %s transaction on card %s".formatted(transactionAdded.transactionType(), transactionAdded.cardNumber()));
        var transactionConfirmed = new TransactionConfirmed(this, transactionAdded.transactionType(), transactionAdded.cardNumber());
        eventPublisher.publishEvent(transactionConfirmed);
    }

}
