package pl.fullstackdeveloper.payments.cqrs.addcard;

import java.time.LocalDate;

public class AddCardResult {

    private String number;
    private LocalDate expiration;

    public AddCardResult(String number, LocalDate expiration) {
        this.number = number;
        this.expiration = expiration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

}
