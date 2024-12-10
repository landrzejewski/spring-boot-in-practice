package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.application.CardNotFoundException;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import static pl.fullstackdeveloper.common.Mappers.mapList;

@Handler
public class GetCardQueryHandler implements QueryHandler<GetCardResult, GetCardQuery> {

    private final CardRepository cardRepository;

    public GetCardQueryHandler(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public GetCardResult handle(GetCardQuery query) {
        var cardNumber = new CardNumber(query.cardNumber());
        return cardRepository.findByNumber(cardNumber)
                .map(this::toResult)
                .orElseThrow(CardNotFoundException::new);
    }

    private GetCardResult toResult(Card card) {
        return new GetCardResult(
                card.getNumber().value(),
                card.getExpiration(),
                card.getCurrency().getCurrencyCode(),
                mapList(card.getTransactions(), CardTransactionResult::from)
        );
    }

}
