package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.common.Money;

import java.time.LocalDate;
import java.util.Currency;

public record CardInfo(String id, String number, LocalDate expiration, Currency currency, Money balance) {
}
