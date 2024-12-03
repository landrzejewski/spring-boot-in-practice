package pl.fullstackdeveloper.payments.infrastructure.time;

import java.time.ZonedDateTime;

public interface DateTimeProvider {

    ZonedDateTime getZonedDateTime();

}
