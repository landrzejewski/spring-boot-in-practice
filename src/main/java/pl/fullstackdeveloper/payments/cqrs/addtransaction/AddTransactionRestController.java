package pl.fullstackdeveloper.payments.cqrs.addtransaction;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.fullstackdeveloper.common.cqrs.Bus;

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
        command.setCardNumber(number);
        bus.executeCommand(command);
        return ResponseEntity.noContent().build();
    }

}
