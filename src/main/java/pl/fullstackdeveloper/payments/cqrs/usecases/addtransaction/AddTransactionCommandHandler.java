package pl.fullstackdeveloper.payments.cqrs.usecases.addtransaction;

import org.springframework.context.ApplicationEventPublisher;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.CommandHandler;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.cqrs.events.TransactionAddedEvent;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import static pl.fullstackdeveloper.payments.domain.TransactionType.INFLOW;
import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

@Handler
public class AddTransactionCommandHandler implements CommandHandler<AddTransactionResult, AddTransactionCommand> {

    private final AddTransactionUseCase addTransactionUseCase;
    private final ApplicationEventPublisher eventPublisher;

    public AddTransactionCommandHandler(AddTransactionUseCase addTransactionUseCase, ApplicationEventPublisher eventPublisher) {
        this.addTransactionUseCase = addTransactionUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AddTransactionResult handle(AddTransactionCommand command) {
        var cardNumber = new CardNumber(command.cardNumber());
        var amount = new Money(command.amount(), command.currencyCode());
        var transactionType = switch (command.type()) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + command.type());
        };
        var transactionId = addTransactionUseCase.handle(cardNumber, amount, transactionType);
        eventPublisher.publishEvent(new TransactionAddedEvent(this, cardNumber, transactionId));
        return new AddTransactionResult(transactionId.toString());
    }

}
