package pl.fullstackdeveloper.payments;

public record TransactionAdded(String cardNumber, String transactionId, String transactionType) {
}
