package pl.fullstackdeveloper.payments.adapters.rest;

import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.AddCardUseCase;
import pl.fullstackdeveloper.payments.adapters.common.web.LocationUri;

import java.util.Currency;

@RestController
final class AddCardRestController {

    private final AddCardUseCase addCardUseCase;

    AddCardRestController(AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @PostMapping("api/cards")
    ResponseEntity<Void> addCard(@Validated @RequestBody final AddCardRequest addCardRequest) {
        var cardNumber = addCardUseCase.handle(addCardRequest.currency());
        var locationUri = LocationUri.fromRequest(cardNumber);
        return ResponseEntity.created(locationUri).build();
    }

}

record AddCardRequest(@Pattern(regexp = "[A-Z]{3}") String currencyCode) {

    Currency currency() {
        return Currency.getInstance(currencyCode);
    }

}
