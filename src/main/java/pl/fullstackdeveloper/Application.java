package pl.fullstackdeveloper;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.payments.PaymentsConfiguration;

import java.util.Currency;
import java.util.logging.Logger;

import static pl.fullstackdeveloper.payments.domain.TransactionType.INFLOW;
import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static final Currency CURRENCY = Currency.getInstance("PLN");

    public static void main(final String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");

        var paymentsConfiguration = new PaymentsConfiguration();
        var addCardUseCase = paymentsConfiguration.addCardUseCase();
        var addTransactionUseCase = paymentsConfiguration.addTransactionUseCase();
        var getCardsUseCase = paymentsConfiguration.getCardsUseCase();
        var getCardUseCase = paymentsConfiguration.getCardUseCase();

        //----------------------------------------------------------------------------------------------

        var cardNumber = addCardUseCase.handle(CURRENCY).getNumber();

        addTransactionUseCase.handle(cardNumber, new Money(200.0, CURRENCY), INFLOW);
        addTransactionUseCase.handle(cardNumber, new Money(100.0, CURRENCY), PAYMENT);

        getCardsUseCase.handle(new PageSpec(0, 10))
                .content()
                .forEach(card -> LOGGER.info(card.toString()));

        getCardUseCase.handle(cardNumber)
                .getTransactions()
                .forEach(t -> LOGGER.info(t.toString()));
    }

}
