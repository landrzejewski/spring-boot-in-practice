package pl.fullstackdeveloper.payments.cqrs.usecases.getcard;

import java.util.List;

public record GetCardResult(String number, List<String> transactions) {
}