package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.application.CardNotFoundException;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

@Handler
public class GetCardQueryHandler implements QueryHandler<CardProjection, GetCardQuery> {

    private final CardRepository cardRepository;

    public GetCardQueryHandler(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardProjection handle(GetCardQuery query) {
        var cardNumber = new CardNumber(query.getCardNumber());
        var card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        return fromCard(card);
    }

    private CardProjection fromCard(Card card) {
        return new CardProjection(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}
