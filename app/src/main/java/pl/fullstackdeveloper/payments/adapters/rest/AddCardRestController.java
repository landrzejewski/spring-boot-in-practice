package pl.fullstackdeveloper.payments.adapters.rest;

import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.adapters.common.web.LocationUri;
import pl.fullstackdeveloper.payments.application.AddCardUseCase;
import pl.fullstackdeveloper.payments.domain.Card;

import java.time.LocalDate;
import java.util.Currency;

@RestController
final class AddCardRestController {

    private final AddCardUseCase addCardUseCase;

    AddCardRestController(final AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @PostMapping("api/cards")
    ResponseEntity<AddCardResponse> addCard(@Validated @RequestBody final AddCardRequest addCardRequest) {
        var card = addCardUseCase.handle(addCardRequest.currency());
        var cardNumber = card.getNumber().value();
        var locationUri = LocationUri.fromRequest(cardNumber);
        return ResponseEntity.created(locationUri).body(AddCardResponse.from(card));
    }

}

record AddCardRequest(@Pattern(regexp = "[A-Z]{3}") String currencyCode) {

    Currency currency() {
        return Currency.getInstance(currencyCode);
    }

}

record AddCardResponse(String number, LocalDate expiration) {

    static AddCardResponse from(final Card card) {
        return new AddCardResponse(card.getNumber().value(), card.getExpiration());
    }

}
