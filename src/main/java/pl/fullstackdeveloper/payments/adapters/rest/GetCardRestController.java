package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.application.GetCardUseCase;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.Transaction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static pl.fullstackdeveloper.common.Mappers.mapList;

@RestController
@RequestMapping("api/cards")
final class GetCardRestController {

    private final GetCardUseCase getCardUseCase;

    GetCardRestController(final GetCardUseCase getCardUseCase) {
        this.getCardUseCase = getCardUseCase;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<GetCardResponse> getCard(@PathVariable final String number) {
        var cardNumber = new CardNumber(number);
        var card = getCardUseCase.handle(cardNumber);
        return ResponseEntity.ok(GetCardResponse.from(card));
    }

}

record GetCardResponse(String number, LocalDate expiration, Double balance, String currencyCode,
                       List<CardTransactionResponse> transactions) {

    static GetCardResponse from(Card card) {
        return new GetCardResponse(
                card.getNumber().value(),
                card.getExpiration(),
                card.getBalance().amount().doubleValue(),
                card.getCurrency().getCurrencyCode(),
                mapList(card.getTransactions(), CardTransactionResponse::from)
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
