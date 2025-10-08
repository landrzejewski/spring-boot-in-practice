package pl.fullstackdeveloper.payments.cqrs.usecases.addcard;

import jakarta.validation.constraints.Pattern;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Command;

public record AddCardCommand(@Pattern(regexp = "[A-Z]{3}") String currencyCode) implements Command {
}
