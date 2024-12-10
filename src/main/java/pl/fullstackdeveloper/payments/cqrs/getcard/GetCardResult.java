package pl.fullstackdeveloper.payments.cqrs.getcard;

import java.time.LocalDate;
import java.util.List;

public record GetCardResult(String number, LocalDate expiration, String currencyCode,  List<CardTransactionResult> transactions) {
}
