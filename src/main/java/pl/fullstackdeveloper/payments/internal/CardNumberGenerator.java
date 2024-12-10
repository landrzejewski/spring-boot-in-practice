package pl.fullstackdeveloper.payments.internal;

import pl.fullstackdeveloper.payments.CardNumber;

public interface CardNumberGenerator {

    CardNumber getNext();

}
