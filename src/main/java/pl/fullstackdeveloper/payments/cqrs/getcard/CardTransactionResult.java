package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.payments.domain.Transaction;

import java.time.Instant;

public record CardTransactionResult(Instant timestamp, Double value, String type) {

    static CardTransactionResult from(Transaction transaction) {
        return new CardTransactionResult(
                transaction.timestamp().toInstant(),
                transaction.value().amount().doubleValue(),
                switch (transaction.type()) {
                    case INFLOW -> "IN";
                    case PAYMENT -> "OUT";
                }
        );
    }

}