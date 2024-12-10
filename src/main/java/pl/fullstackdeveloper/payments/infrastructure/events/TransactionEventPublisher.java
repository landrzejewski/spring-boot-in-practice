package pl.fullstackdeveloper.payments.infrastructure.events;

import pl.fullstackdeveloper.payments.TransactionAdded;

public interface TransactionEventPublisher {

    void publish(TransactionAdded transactionAdded);

}
