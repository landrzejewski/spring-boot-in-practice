package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.CardInfo;
import pl.fullstackdeveloper.payments.GetCardsUseCase;
import pl.fullstackdeveloper.payments.application.GetCardsService;

public class GetCardsServiceAdapter implements GetCardsUseCase {

    private final GetCardsService getCardsService;
    private final PaymentsMapper mapper;

    public GetCardsServiceAdapter(final GetCardsService getCardsService, final PaymentsMapper mapper) {
        this.getCardsService = getCardsService;
        this.mapper = mapper;
    }

    @Override
    public ResultPage<CardInfo> handle(final PageSpec pageSpec) {
        var cards = getCardsService.handle(pageSpec);
        return mapper.map(cards);
    }

}
