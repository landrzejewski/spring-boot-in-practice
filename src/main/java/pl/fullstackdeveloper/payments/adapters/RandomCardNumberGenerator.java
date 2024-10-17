package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.payments.application.CardNumberGenerator;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Random;

public final class RandomCardNumberGenerator implements CardNumberGenerator {

    private final Random random = new Random();
    private final int length;

    public RandomCardNumberGenerator(final int length) {
        this.length = length;
    }

    @Override
    public synchronized CardNumber getNext() {
        var number = new StringBuilder();
        for (int index = 0; index < length; index++) {
            int digit = random.nextInt(10);
            number.append(digit);
        }
        return new CardNumber(number.toString());
    }

}
