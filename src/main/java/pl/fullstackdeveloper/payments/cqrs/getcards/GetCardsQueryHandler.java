package pl.fullstackdeveloper.payments.cqrs.getcards;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;

@Handler
public class GetCardsQueryHandler implements QueryHandler<CardsProjection, GetCardsQuery> {

    private final CardRepository cardRepository;

    public GetCardsQueryHandler(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardsProjection handle(GetCardsQuery query) {
        var pageSpec = new PageSpec(query.getPageNumber(), query.getPageSize());
        var cardPage = cardRepository.findAll(pageSpec);
        return null;
    }

    private CardsProjection fromCard(Card card) {
        return new CardsProjection(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}
