package pl.fullstackdeveloper.payments.cqrs.usecases.getcards;

import java.time.LocalDate;

public record GetCardsQueryResult(String number, LocalDate expiration, Double balance, String currencyCode) {
}