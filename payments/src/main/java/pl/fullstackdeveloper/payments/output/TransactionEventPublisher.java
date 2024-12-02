package pl.fullstackdeveloper.payments.output;

import pl.fullstackdeveloper.payments.application.TransactionAdded;

public interface TransactionEventPublisher {

    void publish(TransactionAdded transactionAdded);

}
