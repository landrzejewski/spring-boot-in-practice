package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.getcard.CardProjection;
import pl.fullstackdeveloper.payments.cqrs.getcard.GetCardQuery;

@RestController
@RequestMapping("api/cards")
final class GetCardRestController {

    private final Bus bus;

    public GetCardRestController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<CardProjection> getCard(@PathVariable final String number) {
        var query = new GetCardQuery(number);
        bus.executeQuery(query);
        return ResponseEntity.ok(bus.executeQuery(query));
    }

}

