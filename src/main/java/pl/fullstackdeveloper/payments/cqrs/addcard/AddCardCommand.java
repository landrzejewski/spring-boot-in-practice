package pl.fullstackdeveloper.payments.cqrs.addcard;

import jakarta.validation.constraints.Pattern;
import pl.fullstackdeveloper.common.cqrs.Command;

public class AddCardCommand implements Command<AddCardResult> {

    @Pattern(regexp = "[A-Z]{3}")
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
