package pl.fullstackdeveloper.payments.application;

import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Optional;

public interface CardRepository {

    Card save(Card card);

    Optional<Card> findByNumber(CardNumber cardNumber);

}
