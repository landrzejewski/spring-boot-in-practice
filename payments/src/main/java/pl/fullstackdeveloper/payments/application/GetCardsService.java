package pl.fullstackdeveloper.payments.application;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.output.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;

public class GetCardsService {

    private final CardRepository cardRepository;

    public GetCardsService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResultPage<Card> handle(final PageSpec pageSpec) {
        return cardRepository.findAll(pageSpec);
    }

}
