package pl.fullstackdeveloper.payments.cqrs.usecases.getcards;

import org.springframework.data.domain.PageRequest;
import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoCardRepository;

@Handler
public class GetCardsQueryHandler implements QueryHandler<ResultPage<GetCardsQueryResult>, GetCardsQuery> {

    private final MongoCardRepository cardRepository;

    public GetCardsQueryHandler(final MongoCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public ResultPage<GetCardsQueryResult> handle(GetCardsQuery query) {
        var pageRequest = PageRequest.of(query.pageNumber(), query.pageSize());
        var cardPage = cardRepository.findAllQueryResults(pageRequest);
        return new ResultPage<>(
                cardPage.getContent(),
                new PageSpec(query.pageNumber(), query.pageSize()),
                cardPage.getTotalPages()
        );
    }

}
