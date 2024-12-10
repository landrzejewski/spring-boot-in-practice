package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.usecases.getcard.GetCardQuery;
import pl.fullstackdeveloper.payments.cqrs.usecases.getcard.GetCardResult;

@RestController
@RequestMapping("api/cards")
final class GetCardRestController {

    private final Bus bus;

    GetCardRestController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<GetCardResult> getCard(@PathVariable final String number,
                                          @RequestParam(required = false, defaultValue = "PLN") String currencyCode) {
        var query = new GetCardQuery(number, currencyCode);
        var result = bus.execute(query);
        return ResponseEntity.ok(result);
    }

}
