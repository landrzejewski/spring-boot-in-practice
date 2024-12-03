package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.common.cqrs.Query;

public record GetCardQuery(String cardNumber) implements Query<CardProjection> {
}
