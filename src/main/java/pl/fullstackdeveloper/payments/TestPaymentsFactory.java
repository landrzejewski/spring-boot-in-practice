package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.payments.adapters.*;
import pl.fullstackdeveloper.payments.application.*;

public class TestPaymentsFactory implements PaymentsFactory {

    private final CardRepository cardRepository = new HashMapCardRepository();
    private final CardNumberGenerator cardNumberGenerator = new SequentialCardNumberGenerator(16);
    // private final CardNumberGenerator cardNumberGenerator = new RandomCardNumberGenerator(16);
    private final DateTimeProvider dateTimeProvider = new SystemDateTimeProvider();
    private final TransactionEventPublisher transactionEventPublisher = new ConsoleTransactionEventPublisher();

    @Override
    public AddCardUseCase addCardUseCase() {
        return new AddCardUseCase(cardNumberGenerator, dateTimeProvider, cardRepository);
    }

    @Override
    public GetCardsUseCase getCardsUseCase() {
        return new GetCardsUseCase(cardRepository);
    }

    @Override
    public GetCardUseCase getCardUseCase() {
        return new GetCardUseCase(cardRepository);
    }

    @Override
    public AddTransactionUseCase addTransactionUseCase() {
        // return new AddTransactionUseCase(dateTimeProvider, transactionEventPublisher, cardRepository);
        return new AddTransactionLoggingProxy(dateTimeProvider, transactionEventPublisher, cardRepository);
    }

}
