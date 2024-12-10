package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.payments.adapters.common.cqrs.Query;

public record GetCardQuery(String cardNumber) implements Query<GetCardResult> {
}
