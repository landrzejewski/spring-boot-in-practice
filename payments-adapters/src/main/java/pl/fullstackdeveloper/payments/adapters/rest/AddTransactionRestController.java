package pl.fullstackdeveloper.payments.adapters.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.AddCardTransactionUseCase;
import pl.fullstackdeveloper.payments.CardTransactionType;

import static pl.fullstackdeveloper.payments.CardTransactionType.INFLOW;
import static pl.fullstackdeveloper.payments.CardTransactionType.PAYMENT;

@RestController
final class AddCardTransactionRestController {

    private final AddCardTransactionUseCase addCardTransactionUseCase;

    AddCardTransactionRestController(AddCardTransactionUseCase addCardTransactionUseCase) {
        this.addCardTransactionUseCase = addCardTransactionUseCase;
    }

    @PostMapping("api/cards/{number:\\d{16,19}}/transactions")
    ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final AddCardTransactionRequest addCardTransactionRequest) {
        var amount = addCardTransactionRequest.money();
        var transactionType = addCardTransactionRequest.transactionType();
        addCardTransactionUseCase.handle(number, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

}

record AddCardTransactionRequest(Double amount,
                                 @Pattern(regexp = "[A-Z]{3}") String currencyCode,
                                 @NotNull String type) {

    Money money() {
        return new Money(amount, currencyCode);
    }

    CardTransactionType transactionType() {
        return switch (type) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
