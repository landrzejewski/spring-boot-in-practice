package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.queries.getcard.GetCardQuery;
import pl.fullstackdeveloper.payments.cqrs.queries.getcard.GetCardResult;

@RestController
@RequestMapping("api/cards")
final class GetCardRestController {

    private final Bus bus;

    GetCardRestController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<GetCardResult> getCard(@PathVariable final String number) {
        var query = new GetCardQuery(number);
        var result = bus.execute(query);
        return ResponseEntity.ok(result);
    }

}
