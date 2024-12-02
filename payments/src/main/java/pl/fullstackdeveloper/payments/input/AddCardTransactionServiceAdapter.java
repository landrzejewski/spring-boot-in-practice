package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.AddCardTransactionUseCase;
import pl.fullstackdeveloper.payments.CardTransactionType;
import pl.fullstackdeveloper.payments.application.AddTransactionService;
import pl.fullstackdeveloper.payments.domain.TransactionType;

public class AddCardTransactionServiceAdapter implements AddCardTransactionUseCase {

    private final AddTransactionService addCardTransactionService;
    private final PaymentsMapper mapper;

    public AddCardTransactionServiceAdapter(AddTransactionService addCardTransactionService, PaymentsMapper mapper) {
        this.addCardTransactionService = addCardTransactionService;
        this.mapper = mapper;
    }

    @Override
    public void handle(String cardNumber, Money amount, CardTransactionType cardTransactionType) {
        var number = mapper.map(cardNumber);
        addCardTransactionService.handle(number, amount, TransactionType.valueOf(cardTransactionType.name()));
    }

}
