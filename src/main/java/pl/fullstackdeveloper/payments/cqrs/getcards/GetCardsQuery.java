package pl.fullstackdeveloper.payments.cqrs.getcards;

import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.common.cqrs.Query;

public record GetCardsQuery(int pageNumber, int pageSize) implements Query<ResultPage<CardsProjection>> {
}
