package pl.fullstackdeveloper.payments.cqrs.usecases.getcard;

import java.math.BigDecimal;

public record GetCardResult(String number, BigDecimal balance) {
}