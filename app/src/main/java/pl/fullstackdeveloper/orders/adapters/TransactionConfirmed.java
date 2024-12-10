package pl.fullstackdeveloper.orders.adapters;

import org.springframework.context.ApplicationEvent;

public class TransactionConfirmed extends ApplicationEvent {

    private final String cardNumber;
    private final String transactionId;

    public TransactionConfirmed(Object source, String cardNumber, String transactionId) {
        super(source);
        this.cardNumber = cardNumber;
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getTransactionId() { return transactionId; }

}
