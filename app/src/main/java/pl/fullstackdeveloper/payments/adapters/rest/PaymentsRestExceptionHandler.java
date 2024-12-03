package pl.fullstackdeveloper.payments.adapters.rest;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.fullstackdeveloper.payments.adapters.common.web.ExceptionResponse;
import pl.fullstackdeveloper.payments.adapters.common.web.RestExceptionResponseBuilder;
import pl.fullstackdeveloper.payments.application.CardNotFoundException;

import java.util.Locale;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = "pl.training.payments.adapters.rest")
final class PaymentsRestExceptionHandler {

    private final RestExceptionResponseBuilder exceptionResponseBuilder;

    PaymentsRestExceptionHandler(final RestExceptionResponseBuilder exceptionResponseBuilder) {
        this.exceptionResponseBuilder = exceptionResponseBuilder;
    }

    @ExceptionHandler(CardNotFoundException.class)
    ResponseEntity<ExceptionResponse> onCardNotFoundException(final CardNotFoundException exception, final Locale locale) {
        return exceptionResponseBuilder.build(exception, NOT_FOUND, locale);
    }

}
