package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;

public interface GetCardsUseCase {

    ResultPage<CardInfo> handle(PageSpec pageSpec);

}
