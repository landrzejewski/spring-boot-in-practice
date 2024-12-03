package pl.fullstackdeveloper.payments.cqrs.addcard;

import java.time.LocalDate;

public record AddCardResult(String number, LocalDate expiration) {
}
