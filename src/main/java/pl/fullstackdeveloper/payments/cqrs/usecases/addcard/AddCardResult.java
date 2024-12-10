package pl.fullstackdeveloper.payments.cqrs.usecases.addcard;

import java.time.LocalDate;

public record AddCardResult(String number, LocalDate expiration) {
}
