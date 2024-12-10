package pl.fullstackdeveloper.payments.cqrs.usecases.addcard;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.CommandHandler;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;

import java.util.Currency;

@Handler
public class AddCardHandler implements CommandHandler<AddCardResult, AddCardCommand> {

    private final AddCardUseCase addCardUseCase;

    public AddCardHandler(AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @Override
    public AddCardResult handle(AddCardCommand command) {
        var currency = Currency.getInstance(command.currencyCode());
        var card = addCardUseCase.handle(currency);
        return new AddCardResult(card.getNumber().value(), card.getExpiration());
    }

}
