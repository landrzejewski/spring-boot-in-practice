package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.TransactionAdded;
import pl.fullstackdeveloper.payments.output.TransactionEventPublisher;

import java.util.logging.Logger;

@Adapter
final class ConsoleTransactionEventPublisher implements TransactionEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(ConsoleTransactionEventPublisher.class.getName());

    @Override
    public void publish(final TransactionAdded transactionAdded) {
        LOGGER.info("New %s transaction on card %s".formatted(transactionAdded.transactionType(), transactionAdded.cardNumber()));
    }

}
