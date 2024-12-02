package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.CardInfo;
import pl.fullstackdeveloper.payments.GetCardsUseCase;

import java.time.LocalDate;

@RestController
final class GetCardsRestController {

    private final GetCardsUseCase getCardsUseCase;

    public GetCardsRestController(GetCardsUseCase getCardsUseCase) {
        this.getCardsUseCase = getCardsUseCase;
    }

    @GetMapping("api/cards")
    ResponseEntity<ResultPage<GetCardsResponse>> getCards(
            @RequestParam(required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(required = false, defaultValue = "10") final int pageSize) {
        var pageSpec = new PageSpec(pageNumber, pageSize);
        var response = getCardsUseCase.handle(pageSpec).map(GetCardsResponse::from);
        return ResponseEntity.ok(response);
    }

}

record GetCardsResponse(String number, LocalDate expiration, Double balance, String currencyCode) {

    static GetCardsResponse from(CardInfo cardInfo) {
        return new GetCardsResponse(
                cardInfo.number(),
                cardInfo.expiration(),
                cardInfo.balance().amount().doubleValue(),
                cardInfo.currency().getCurrencyCode()
        );
    }

}
