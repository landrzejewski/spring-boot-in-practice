package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.application.GetCardUseCase;
import pl.fullstackdeveloper.payments.application.GetCardsUseCase;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.TransactionId;
import pl.fullstackdeveloper.payments.domain.TransactionType;

import java.util.Currency;

public class PaymentsFacade {

    private final AddCardUseCase addCardUseCase;
    private final AddTransactionUseCase  addTransactionUseCase;
    private final GetCardsUseCase getCardsUseCase;
    private final GetCardUseCase getCardUseCase;

    public PaymentsFacade(AddCardUseCase addCardUseCase, AddTransactionUseCase addTransactionUseCase, GetCardsUseCase getCardsUseCase, GetCardUseCase getCardUseCase) {
        this.addCardUseCase = addCardUseCase;
        this.addTransactionUseCase = addTransactionUseCase;
        this.getCardsUseCase = getCardsUseCase;
        this.getCardUseCase = getCardUseCase;
    }

    public Card addCard(Currency currency) {
        return addCardUseCase.handle(currency);
    }

    public TransactionId addTransaction(CardNumber cardNumber, Money value, TransactionType transactionType) {
        return addTransactionUseCase.handle(cardNumber, value, transactionType);
    }

    public ResultPage<Card> getCards(PageSpec pageSpec) {
        return getCardsUseCase.handle(pageSpec);
    }

    public Card getCard(CardNumber cardNumber) {
        return getCardUseCase.handle(cardNumber);
    }

}
