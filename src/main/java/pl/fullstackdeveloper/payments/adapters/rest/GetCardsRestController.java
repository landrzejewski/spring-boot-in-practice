package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.getcards.CardsProjection;
import pl.fullstackdeveloper.payments.cqrs.getcards.GetCardsQuery;

@RestController
final class GetCardsRestController {

    private final Bus bus;

    public GetCardsRestController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("api/cards")
    ResponseEntity<ResultPage<CardsProjection>> getCards(@RequestBody final GetCardsQuery query) {
        return ResponseEntity.ok(bus.executeQuery(query));
    }

}

