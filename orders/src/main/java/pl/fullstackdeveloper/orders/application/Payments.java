package pl.fullstackdeveloper.orders.application;

import java.util.Currency;

public interface Payments {

    String pay(Double amount, Currency currency, PaymentDetails details);

}
