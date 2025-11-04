package pl.fullstackdeveloper.payments;

import org.springframework.modulith.events.Externalized;

//@Externalized()
public record TransactionAdded(String cardNumber, String transactionId, String transactionType) {
}
