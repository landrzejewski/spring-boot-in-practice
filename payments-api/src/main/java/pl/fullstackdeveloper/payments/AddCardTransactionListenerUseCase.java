package pl.fullstackdeveloper.payments;

import java.util.function.Consumer;

public interface AddCardTransactionListenerUseCase {

    void addListener(Consumer<String> listener);

}
