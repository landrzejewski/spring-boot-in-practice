package pl.fullstackdeveloper.orders.application;

import java.util.Currency;

public interface PaymentsApi {

    String pay(Double amount, Currency currency, PaymentDetails paymentDetails);

}
