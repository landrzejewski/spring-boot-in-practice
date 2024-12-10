package pl.fullstackdeveloper.payments.cqrs.usecases.addtransaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Command;

public record AddTransactionCommand(String cardNumber, Double amount, @Pattern(regexp = "[A-Z]{3}") String currencyCode,
                                    @NotNull String type) implements Command<AddTransactionResult> {

    public AddTransactionCommand with(String cardNumber) {
        return new AddTransactionCommand(cardNumber, amount, currencyCode, type);
    }

}
