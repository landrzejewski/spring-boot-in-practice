package pl.fullstackdeveloper.payments.cqrs.addcard;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.adapters.common.web.LocationUri;

@RestController
final class AddCardRestController {

    private final Bus bus;

    public AddCardRestController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping("api/cards")
    ResponseEntity<AddCardResult> addCard(@Validated @RequestBody final AddCardCommand command) {
        var result = bus.executeCommand(command);
        var locationUri = LocationUri.fromRequest(result.getNumber());
        return ResponseEntity.created(locationUri).body(result);
    }

}
