package pl.fullstackdeveloper.payments.adapters.rest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.Money;
import pl.fullstackdeveloper.payments.application.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.TransactionType;

import static pl.fullstackdeveloper.payments.domain.TransactionType.INFLOW;
import static pl.fullstackdeveloper.payments.domain.TransactionType.PAYMENT;

@RestController
final class AddCardTransactionRestController {

    private final AddTransactionUseCase addTransactionUseCase;

    AddCardTransactionRestController(final AddTransactionUseCase addTransactionUseCase) {
        this.addTransactionUseCase = addTransactionUseCase;
    }

    @PostMapping("api/cards/{number:\\d{16,19}}/transactions")
    ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final AddCardTransactionRequest addCardTransactionRequest) {
        var cardNumber = new CardNumber(number);
        var amount = addCardTransactionRequest.money();
        var transactionType = addCardTransactionRequest.transactionType();
        addTransactionUseCase.handle(cardNumber, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}

record AddCardTransactionRequest(@Min(100) Double amount,
                                 @Pattern(regexp = "[A-Z]{3}") String currencyCode,
                                 @NotNull String type) {

    Money money() {
        return new Money(amount, currencyCode);
    }

    TransactionType transactionType() {
        return switch (type) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
