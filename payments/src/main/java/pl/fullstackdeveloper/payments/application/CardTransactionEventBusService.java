package pl.fullstackdeveloper.payments.application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CardTransactionEventBusService {

    private final List<Consumer<String>> listeners = new ArrayList<>();

    public void addListener(final Consumer<String> listener) {
        listeners.add(listener);
    }

    public void notifyAll(final String cardNumber) {
        listeners.forEach(listener -> listener.accept(cardNumber));
    }

}
