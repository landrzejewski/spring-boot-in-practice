package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.ApplicationEvent;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.Transaction;

public class TransactionAddedEvent extends ApplicationEvent {

    private final CardNumber cardNumber;
    private final Transaction transaction;

    public TransactionAddedEvent(Object source, CardNumber cardNumber, Transaction transaction) {
        super(source);
        this.cardNumber = cardNumber;
        this.transaction = transaction;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public Transaction getTransaction() {
        return transaction;
    }

}
