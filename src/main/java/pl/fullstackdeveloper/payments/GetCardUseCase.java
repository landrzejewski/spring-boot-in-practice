package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.common.annotations.Atomic;
import pl.fullstackdeveloper.payments.infrastructure.persistence.CardRepository;

@Atomic
public class GetCardUseCase {

    private final CardRepository cardRepository;

    public GetCardUseCase(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card handle(final CardNumber cardNumber) {
        return cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
