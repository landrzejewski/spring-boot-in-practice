package pl.fullstackdeveloper.payments.cqrs.getcards;

import java.time.LocalDate;

public record CardsProjection(String number, LocalDate expiration, Double balance, String currencyCode) {
}