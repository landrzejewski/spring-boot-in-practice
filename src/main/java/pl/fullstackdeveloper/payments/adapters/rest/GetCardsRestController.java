package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.queries.getcards.GetCardsQuery;
import pl.fullstackdeveloper.payments.cqrs.queries.getcards.GetCardsQueryResult;

@RestController
final class GetCardsRestController {

    private final Bus bus;

    GetCardsRestController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("api/cards")
    ResponseEntity<ResultPage<GetCardsQueryResult>> getCards(@RequestBody final GetCardsQuery query) {
        return ResponseEntity.ok(bus.execute(query));
    }

}

