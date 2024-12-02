package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.payments.AddCardUseCase;
import pl.fullstackdeveloper.payments.application.AddCardService;

import java.util.Currency;

public class AddCardServiceAdapter implements AddCardUseCase {

    private final AddCardService addCardService;

    public AddCardServiceAdapter(final AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @Override
    public String handle(final Currency currency) {
        return addCardService.handle(currency).getId().value().toString();
    }

}
