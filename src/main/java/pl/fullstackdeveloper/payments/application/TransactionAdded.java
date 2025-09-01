package pl.fullstackdeveloper.payments.application;

public record TransactionAdded(String cardNumber, String transactionId, String transactionType) {
}
