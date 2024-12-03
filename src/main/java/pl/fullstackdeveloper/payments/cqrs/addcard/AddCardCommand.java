package pl.fullstackdeveloper.payments.cqrs.addcard;

import jakarta.validation.constraints.Pattern;
import pl.fullstackdeveloper.common.cqrs.Command;

public record AddCardCommand(@Pattern(regexp = "[A-Z]{3}") String currencyCode) implements Command<AddCardResult> {
}
