package pl.fullstackdeveloper.payments.adapters;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;
import pl.fullstackdeveloper.payments.application.CardNumberGenerator;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.application.DateTimeProvider;
import pl.fullstackdeveloper.payments.domain.Card;

import java.util.Currency;

//@Transactional
//@Service
public class AddCardUseCaseTxProxy extends AddCardUseCase {

    private final AddCardUseCase addCardUseCase;

    public AddCardUseCaseTxProxy(CardNumberGenerator cardNumberGenerator, DateTimeProvider dateTimeProvider, CardRepository cardRepository, AddCardUseCase addCardUseCase) {
        super(cardNumberGenerator, dateTimeProvider, cardRepository);
        this.addCardUseCase = addCardUseCase;
    }

    @Override
    public Card handle(Currency currency) {
        return addCardUseCase.handle(currency);
    }

}
