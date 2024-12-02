package pl.fullstackdeveloper.payments.adapters.common.web;

import java.time.ZonedDateTime;

public record ExceptionResponse(String message, ZonedDateTime timestamp) {

    public ExceptionResponse(String message) {
        this(message, ZonedDateTime.now());
    }

}
