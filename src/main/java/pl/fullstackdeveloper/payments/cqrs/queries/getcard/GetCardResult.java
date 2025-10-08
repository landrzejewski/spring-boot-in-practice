package pl.fullstackdeveloper.payments.cqrs.queries.getcard;

import java.util.List;

public record GetCardResult(String number, List<String> transactions) {
}