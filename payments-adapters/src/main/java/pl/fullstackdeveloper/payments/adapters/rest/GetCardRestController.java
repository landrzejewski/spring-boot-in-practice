package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.CardInfo;
import pl.fullstackdeveloper.payments.GetCardUseCase;
import pl.fullstackdeveloper.payments.domain.Transaction;

import java.time.Instant;
import java.time.LocalDate;

@RestController
@RequestMapping("api/cards")
final class GetCardRestController {

    private final GetCardUseCase getCardUseCase;

    GetCardRestController(GetCardUseCase getCardUseCase) {
        this.getCardUseCase = getCardUseCase;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<GetCardResponse> getCard(@PathVariable final String number) {
        var card = getCardUseCase.handle(number);
        return ResponseEntity.ok(GetCardResponse.from(card));
    }

}

record GetCardResponse(String number, LocalDate expiration, Double balance, String currencyCode) {

    static GetCardResponse from(CardInfo cardInfo) {
        return new GetCardResponse(
                cardInfo.number(),
                cardInfo.expiration(),
                cardInfo.balance().amount().doubleValue(),
                cardInfo.currency().getCurrencyCode()
        );
    }

}

record CardTransactionResponse(Instant timestamp, Double value, String type) {

    static CardTransactionResponse from(Transaction transaction) {
        return new CardTransactionResponse(
                transaction.timestamp().toInstant(),
                transaction.value().amount().doubleValue(),
                switch (transaction.type()) {
                    case INFLOW -> "IN";
                    case PAYMENT -> "OUT";
                }
        );
    }

}
