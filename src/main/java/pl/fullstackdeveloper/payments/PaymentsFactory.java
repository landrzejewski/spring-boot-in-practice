package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.payments.adapters.PaymentsFacade;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.application.GetCardUseCase;
import pl.fullstackdeveloper.payments.application.GetCardsUseCase;

public interface PaymentsFactory {

    AddCardUseCase addCardUseCase();

    GetCardsUseCase getCardsUseCase();

    GetCardUseCase getCardUseCase();

    AddTransactionUseCase addTransactionUseCase();

}
