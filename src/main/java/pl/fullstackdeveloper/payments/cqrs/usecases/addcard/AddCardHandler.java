package pl.fullstackdeveloper.payments.cqrs.usecases.addcard;

import org.springframework.context.ApplicationEventPublisher;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.CommandHandler;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;
import pl.fullstackdeveloper.payments.cqrs.events.CardAddedEvent;

import java.util.Currency;

@Handler
public class AddCardHandler implements CommandHandler<AddCardCommand> {

    private final AddCardUseCase addCardUseCase;
    private final ApplicationEventPublisher eventPublisher;

    public AddCardHandler(AddCardUseCase addCardUseCase, ApplicationEventPublisher eventPublisher) {
        this.addCardUseCase = addCardUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(AddCardCommand command) {
        var currency = Currency.getInstance(command.currencyCode());
        var card = addCardUseCase.handle(currency);
        eventPublisher.publishEvent(new CardAddedEvent(this, card));
    }

}
