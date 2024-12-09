package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.application.DateTimeProvider;
import pl.fullstackdeveloper.payments.application.TransactionEventPublisher;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.TransactionId;
import pl.fullstackdeveloper.payments.domain.TransactionType;

import java.util.logging.Logger;

public final class AddTransactionLoggingProxy extends AddTransactionUseCase {

    private static final Logger LOGGER = Logger.getLogger(AddTransactionLoggingProxy.class.getName());

    public AddTransactionLoggingProxy(final DateTimeProvider dateTimeProvider,
                                      final TransactionEventPublisher transactionEventPublisher,
                                      final CardRepository cardRepository) {
        super(dateTimeProvider, transactionEventPublisher, cardRepository);
    }

    @Override
    public TransactionId handle(CardNumber cardNumber, Money value, TransactionType transactionType) {
        LOGGER.info("----------------------------- Transaction start -----------------------------");
        try {
            var transactionId = super.handle(cardNumber, value, transactionType);
            LOGGER.info("Transaction on card %s successfully completed".formatted(cardNumber.value()));
            return transactionId;
        } catch (RuntimeException runtimeException) {
            LOGGER.info("Transaction on card %s failed with exception: %s"
                    .formatted(cardNumber.value(), runtimeException.getClass().getSimpleName()));
            throw runtimeException;
        } finally {
            LOGGER.info("------------------------------ Transaction end ------------------------------\n");
        }
    }

}
