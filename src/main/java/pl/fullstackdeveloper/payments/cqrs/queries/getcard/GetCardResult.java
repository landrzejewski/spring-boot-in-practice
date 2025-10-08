package pl.fullstackdeveloper.payments.cqrs.queries.getcard;

import java.math.BigDecimal;

public record GetCardResult(String number, BigDecimal balance) {
}