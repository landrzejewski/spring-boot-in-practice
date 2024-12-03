package pl.fullstackdeveloper.payments.cqrs.getcard;

import java.time.LocalDate;

public interface CardProjection {

    String getNumber();

    LocalDate getExpiration();

    String getCurrencyCode();

}