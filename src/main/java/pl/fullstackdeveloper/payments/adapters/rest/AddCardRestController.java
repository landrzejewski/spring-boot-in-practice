package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.usecases.addcard.AddCardCommand;

@RestController
final class AddCardRestController {

    private final Bus bus;

    AddCardRestController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping("api/cards")
    ResponseEntity<Void> addCard(@Validated @RequestBody final AddCardCommand command) {
        bus.execute(command);
        return ResponseEntity.noContent().build();
    }

}