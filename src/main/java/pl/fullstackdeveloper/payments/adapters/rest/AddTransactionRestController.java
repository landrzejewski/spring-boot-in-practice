package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Bus;
import pl.fullstackdeveloper.payments.cqrs.usecases.addtransaction.AddTransactionCommand;

@RestController
final class AddTransactionRestController {

    private final Bus bus;

    public AddTransactionRestController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping("api/cards/{number:\\d{16,19}}/transactions")
    ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final AddTransactionCommand command) {
        bus.execute(command.with(number));
        return ResponseEntity.noContent().build();
    }

}
