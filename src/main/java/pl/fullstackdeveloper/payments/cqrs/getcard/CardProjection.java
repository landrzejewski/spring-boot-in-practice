package pl.fullstackdeveloper.payments.cqrs.getcard;

import java.time.LocalDate;

public record CardProjection(String number, LocalDate expiration, Double balance, String currencyCode) {
}