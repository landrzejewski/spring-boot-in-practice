package pl.fullstackdeveloper.payments.application;

import pl.fullstackdeveloper.common.annotations.Atomic;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardId;

import java.time.LocalDate;
import java.util.Currency;

@Atomic
public class AddCardUseCase {

    private static final int EXPIRATION_TIME_IN_YEARS = 1;

    private final CardNumberGenerator cardNumberGenerator;
    private final DateTimeProvider dateTimeProvider;
    private final CardRepository cardRepository;

    public AddCardUseCase(final CardNumberGenerator cardNumberGenerator,
                          final DateTimeProvider dateTimeProvider,
                          final CardRepository cardRepository) {
        this.cardNumberGenerator = cardNumberGenerator;
        this.dateTimeProvider = dateTimeProvider;
        this.cardRepository = cardRepository;
    }

    //@Atomic
    public Card handle(final Currency currency) {
        var card = createCard(currency);
        return cardRepository.save(card);
    }

    private Card createCard(final Currency currency) {
        return new Card(new CardId(), cardNumberGenerator.getNext(), calculateExpirationDate(), currency);
    }

    private LocalDate calculateExpirationDate() {
        return dateTimeProvider.getZonedDateTime()
                .plusYears(EXPIRATION_TIME_IN_YEARS)
                .toLocalDate();
    }

}
