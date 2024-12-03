package pl.fullstackdeveloper.payments.cqrs.addtransaction;

import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.common.cqrs.CommandHandler;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import static pl.fullstackdeveloper.payments.domain.TransactionType.INFLOW;
import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

@Handler
public class AddTransactionCommandHandler implements CommandHandler<AddTransactionResult, AddTransactionCommand> {

    private final AddTransactionUseCase addTransactionUseCase;

    public AddTransactionCommandHandler(AddTransactionUseCase addTransactionUseCase) {
        this.addTransactionUseCase = addTransactionUseCase;
    }

    @Override
    public AddTransactionResult handle(AddTransactionCommand command) {
        var cardNumber = new CardNumber(command.getCardNumber());
        var amount = new Money(command.getAmount(), command.getCurrencyCode());
        var transactionType = switch (command.getType()) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + command.getType());
        };
        var transactionId = addTransactionUseCase.handle(cardNumber, amount, transactionType);
        return new AddTransactionResult(transactionId.toString());
    }

}
