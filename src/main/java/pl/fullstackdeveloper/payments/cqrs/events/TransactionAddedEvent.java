package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.ApplicationEvent;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.TransactionId;

public class TransactionAddedEvent extends ApplicationEvent {

    private final CardNumber cardNumber;
    private final TransactionId transactionId;

    public TransactionAddedEvent(Object source, CardNumber cardNumber, TransactionId transactionId) {
        super(source);
        this.cardNumber = cardNumber;
        this.transactionId = transactionId;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

}
