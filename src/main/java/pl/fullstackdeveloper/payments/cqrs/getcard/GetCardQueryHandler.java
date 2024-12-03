package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.application.CardNotFoundException;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.CardNumber;

@Handler
public class GetCardQueryHandler implements QueryHandler<CardProjection, GetCardQuery> {

    private final CardRepository cardRepository;

    public GetCardQueryHandler(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardProjection handle(GetCardQuery query) {
        var cardNumber = new CardNumber(query.cardNumber());
        return cardRepository.findProjectionByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
