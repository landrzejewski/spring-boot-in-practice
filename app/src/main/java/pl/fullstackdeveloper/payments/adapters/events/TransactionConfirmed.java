package pl.fullstackdeveloper.payments.adapters.events;

public record TransactionConfirmed(String cardNumber, String transactionId) {
}
