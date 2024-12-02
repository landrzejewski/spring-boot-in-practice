package pl.fullstackdeveloper.payments.output;

import pl.fullstackdeveloper.payments.domain.CardNumber;

public interface CardNumberGenerator {

    CardNumber getNext();

}
