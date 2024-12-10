package pl.fullstackdeveloper.payments.cqrs.usecases.getcard;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.application.CardNotFoundException;
import pl.fullstackdeveloper.payments.cqrs.readmodel.CardDocument;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoCardRepository;

@Handler
public class GetCardQueryHandler implements QueryHandler<GetCardResult, GetCardQuery> {

    private final MongoCardRepository cardRepository;

    public GetCardQueryHandler(MongoCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public GetCardResult handle(GetCardQuery query) {
        return cardRepository.findByNumber(query.cardNumber())
                .map(this::toResult)
                .orElseThrow(CardNotFoundException::new);
    }

    private GetCardResult toResult(CardDocument cardDocument) {
        return new GetCardResult(
                cardDocument.getNumber(),
                cardDocument.getTransactions()
        );
    }

}
