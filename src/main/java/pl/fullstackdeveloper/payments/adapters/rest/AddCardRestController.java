package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.adapters.common.web.LocationUri;
import pl.fullstackdeveloper.payments.cqrs.usecases.addcard.AddCardCommand;
import pl.fullstackdeveloper.payments.cqrs.usecases.addcard.AddCardResult;

@RestController
final class AddCardRestController {

    private final Bus bus;

    AddCardRestController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping("api/cards")
    ResponseEntity<AddCardResult> addCard(@Validated @RequestBody final AddCardCommand command) {
        var result = bus.execute(command);
        var locationUri = LocationUri.fromRequest(result.number());
        return ResponseEntity.created(locationUri).body(result);
    }

}