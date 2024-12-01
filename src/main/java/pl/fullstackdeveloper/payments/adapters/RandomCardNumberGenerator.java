package pl.fullstackdeveloper.payments.adapters;

import org.springframework.beans.factory.annotation.Value;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.CardNumberGenerator;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Random;

@Adapter
final class RandomCardNumberGenerator implements CardNumberGenerator {

    private final Random random = new Random();
    private final int length;

    RandomCardNumberGenerator(@Value("${card-number-length}") final int length) {
        this.length = length;
    }

    @Override
    public CardNumber getNext() {
        var number = new StringBuilder();
        for (int index = 0; index < length; index++) {
            int digit = random.nextInt(10);
            number.append(digit);
        }
        return new CardNumber(number.toString());
    }

}
