package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.payments.AddCardTransactionListenerUseCase;
import pl.fullstackdeveloper.payments.application.CardTransactionEventBusService;

import java.util.function.Consumer;

public class AddCardTransactionListenerServiceAdapter implements AddCardTransactionListenerUseCase {

    private final CardTransactionEventBusService cardTransactionEventBusService;

    public AddCardTransactionListenerServiceAdapter(final CardTransactionEventBusService cardTransactionEventBusService) {
        this.cardTransactionEventBusService = cardTransactionEventBusService;
    }

    @Override
    public void addListener(final Consumer<String> listener) {
        cardTransactionEventBusService.addListener(listener);
    }

}
