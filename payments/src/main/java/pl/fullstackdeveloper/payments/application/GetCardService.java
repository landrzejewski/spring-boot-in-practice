package pl.fullstackdeveloper.payments.application;

import pl.fullstackdeveloper.payments.output.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

public class GetCardService {

    private final CardRepository cardRepository;

    public GetCardService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card handle(final CardNumber cardNumber) {
        return cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
