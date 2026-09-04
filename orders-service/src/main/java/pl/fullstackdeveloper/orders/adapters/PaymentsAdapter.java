package pl.fullstackdeveloper.orders.adapters;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.orders.application.ConfirmOrderUseCase;
import pl.fullstackdeveloper.orders.application.PaymentDetails;
import pl.fullstackdeveloper.orders.application.Payments;
import pl.fullstackdeveloper.orders.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Currency;

import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

@Adapter
public class PaymentsAdapter implements Payments {

    private final ConfirmOrderUseCase confirmOrderUseCase;
    private final RestTemplate restTemplate;

    public PaymentsAdapter(ConfirmOrderUseCase confirmOrderUseCase, RestTemplate restTemplate) {
        this.confirmOrderUseCase = confirmOrderUseCase;
        this.restTemplate = restTemplate;
    }

    @Override
    public String pay(Double amount, Currency currency, PaymentDetails details) {
        var paymentRequest = new PaymentRequest(amount, currency.getCurrencyCode(), "OUT");
        restTemplate.postForObject("http://PAYMENTS/api/cards/0503455605699403/transactions", paymentRequest, Void.class);
        return "";
    }

    @Async
    @EventListener
    public void onTransactionConfirmed(TransactionConfirmed transactionConfirmed) {
        confirmOrderUseCase.handle(transactionConfirmed.getTransactionId());
    }

}

record PaymentRequest(Double amount, String currencyCode, String type) {
}