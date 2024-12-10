package pl.fullstackdeveloper.payments.cqrs.usecases.getcard;

import pl.fullstackdeveloper.payments.domain.Transaction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record GetCardResult(String number, LocalDate expiration, String currencyCode,  List<CardTransactionResult> transactions) {
}

record CardTransactionResult(Instant timestamp, Double value, String type) {

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