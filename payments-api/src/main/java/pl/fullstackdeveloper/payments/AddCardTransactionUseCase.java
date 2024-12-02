package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.common.Money;

public interface AddCardTransactionUseCase {

    void handle(String cardNumber, Money amount, CardTransactionType cardTransactionType);

}
