package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.payments.application.TransactionAdded;
import pl.fullstackdeveloper.payments.application.TransactionEventPublisher;

import java.util.logging.Logger;

public final class ConsoleTransactionEventPublisher implements TransactionEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(ConsoleTransactionEventPublisher.class.getName());

    @Override
    public void publish(final TransactionAdded transactionAdded) {
        LOGGER.info("Event: %s transaction added".formatted(transactionAdded.transactionType()));
    }

}
