package pl.fullstackdeveloper.orders.adapters;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.orders.application.PaymentDetails;
import pl.fullstackdeveloper.orders.application.Payments;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Currency;

import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

@Adapter
public class PaymentsAdapter implements Payments {

    private final AddTransactionUseCase addTransactionUseCase;

    public PaymentsAdapter(AddTransactionUseCase addTransactionUseCase) {
        this.addTransactionUseCase = addTransactionUseCase;
    }

    @Override
    public String pay(Double amount, Currency currency, PaymentDetails paymentDetails) {
        var cardNumber = new CardNumber(paymentDetails.cardNumber());
        var money = new Money(amount, currency);
        var transactionId = addTransactionUseCase.handle(cardNumber, money, PAYMENT);
        return transactionId.asString();
    }

}
