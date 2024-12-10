package pl.fullstackdeveloper.payments.infrastructure.persistence;

import pl.fullstackdeveloper.common.model.PageSpec;
import pl.fullstackdeveloper.common.model.ResultPage;
import pl.fullstackdeveloper.payments.Card;
import pl.fullstackdeveloper.payments.CardNumber;

import java.util.Optional;

public interface CardRepository {

    Card save(Card card);

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(CardNumber cardNumber);

}
