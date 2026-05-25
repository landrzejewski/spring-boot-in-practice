package pl.fullstackdeveloper.payments.application;

import pl.fullstackdeveloper.payments.common.PageSpec;
import pl.fullstackdeveloper.payments.common.ResultPage;
import pl.fullstackdeveloper.payments.common.Atomic;
import pl.fullstackdeveloper.payments.domain.Card;

@Atomic
public class GetCardsUseCase {

    private final CardRepository cardRepository;

    public GetCardsUseCase(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResultPage<Card> handle(final PageSpec pageSpec) {
        return cardRepository.findAll(pageSpec);
    }

}
