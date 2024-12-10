package pl.fullstackdeveloper.orders.adapters;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.orders.application.ConfirmOrderUseCase;
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
    private final ConfirmOrderUseCase confirmOrderUseCase;

    public PaymentsAdapter(AddTransactionUseCase addTransactionUseCase, ConfirmOrderUseCase confirmOrderUseCase) {
        this.addTransactionUseCase = addTransactionUseCase;
        this.confirmOrderUseCase = confirmOrderUseCase;
    }

    @Override
    public String pay(Double amount, Currency currency, PaymentDetails details) {
        var value = new Money(amount, currency);
        var cardNumber = new CardNumber(details.cardNumber());
        var transactionId = addTransactionUseCase.handle(cardNumber, value, PAYMENT);
        return transactionId.asText();
    }

    @Async
    @EventListener
    public void onTransactionConfirmed(TransactionConfirmed transactionConfirmed) {
        confirmOrderUseCase.handle(transactionConfirmed.getTransactionId());
    }

}
