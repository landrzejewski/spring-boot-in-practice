package pl.fullstackdeveloper.payments;

import pl.fullstackdeveloper.common.model.PageSpec;
import pl.fullstackdeveloper.common.model.ResultPage;
import pl.fullstackdeveloper.common.annotations.Atomic;
import pl.fullstackdeveloper.payments.infrastructure.persistence.CardRepository;

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
