package pl.fullstackdeveloper.payments.cqrs.queries.getcards;

import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.Query;

public record GetCardsQuery(int pageNumber, int pageSize) implements Query<ResultPage<GetCardsQueryResult>> {
}
