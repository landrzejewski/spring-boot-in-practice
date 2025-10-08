package pl.fullstackdeveloper.payments.cqrs.queries.getcard;

import pl.fullstackdeveloper.payments.adapters.common.cqrs.Query;

public record GetCardQuery(String cardNumber, String currencyCode) implements Query<GetCardResult> {
}
