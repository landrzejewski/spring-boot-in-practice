package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.payments.CardInfo;
import pl.fullstackdeveloper.payments.GetCardUseCase;
import pl.fullstackdeveloper.payments.application.GetCardService;

public class GetCardServiceAdapter implements GetCardUseCase {

    private final GetCardService getCardService;
    private final PaymentsMapper mapper;

    public GetCardServiceAdapter(final GetCardService getCardService, final PaymentsMapper mapper) {
        this.getCardService = getCardService;
        this.mapper = mapper;
    }

    @Override
    public CardInfo handle(final String cardNumber) {
        var number = mapper.map(cardNumber);
        var card = getCardService.handle(number);
        return mapper.map(card);
    }

}
