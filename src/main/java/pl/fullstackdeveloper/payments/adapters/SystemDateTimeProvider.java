package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.DateTimeProvider;

import java.time.ZonedDateTime;

@Adapter
final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
