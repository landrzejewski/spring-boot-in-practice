package pl.fullstackdeveloper.payments.cqrs.addtransaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pl.fullstackdeveloper.common.cqrs.Command;

public class AddTransactionCommand implements Command<AddTransactionResult> {

    private String cardNumber;
    private Double amount;
    @Pattern(regexp = "[A-Z]{3}")
    private String currencyCode;
    @NotNull
    private String type;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
