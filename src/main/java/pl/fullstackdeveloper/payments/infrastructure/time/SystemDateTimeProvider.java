package pl.fullstackdeveloper.payments.infrastructure.time;

import pl.fullstackdeveloper.common.annotations.Adapter;

import java.time.ZonedDateTime;

@Adapter
public final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
