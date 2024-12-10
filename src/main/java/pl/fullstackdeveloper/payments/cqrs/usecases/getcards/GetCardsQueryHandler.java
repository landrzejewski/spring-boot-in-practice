package pl.fullstackdeveloper.payments.cqrs.usecases.getcards;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;

@Handler
public class GetCardsQueryHandler implements QueryHandler<ResultPage<GetCardsQueryResult>, GetCardsQuery> {

    private final CardRepository cardRepository;

    public GetCardsQueryHandler(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public ResultPage<GetCardsQueryResult> handle(GetCardsQuery query) {
        var pageSpec = new PageSpec(query.pageNumber(), query.pageSize());
        var cardPage = cardRepository.findAll(pageSpec);
        return cardPage.map(this::fromCard);
    }

    private GetCardsQueryResult fromCard(Card card) {
        return new GetCardsQueryResult(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}
