package pl.fullstackdeveloper.payments.cqrs.addcard;

import pl.fullstackdeveloper.common.cqrs.CommandHandler;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;

import java.util.Currency;

@Handler
public class AddCardCommandHandler implements CommandHandler<AddCardResult, AddCardCommand> {

    private final AddCardUseCase addCardUseCase;

    public AddCardCommandHandler(AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @Override
    public AddCardResult handle(AddCardCommand command) {
        var currency = Currency.getInstance(command.getCurrencyCode());
        var card = addCardUseCase.handle(currency);
        return new AddCardResult(card.getNumber().value(), card.getExpiration());
    }

}
