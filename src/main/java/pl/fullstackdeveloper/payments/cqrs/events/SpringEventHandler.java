package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.payments.cqrs.readmodel.CardDocument;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoCardRepository;

@Component
public class SpringEventHandler {

    private final MongoCardRepository cardRepository;

    public SpringEventHandler(MongoCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Async
    @EventListener
    public void onCardAdded(CardAddedEvent event) {
        var card = event.getCard();
        var cardDocument = new CardDocument(card.getId().value(), card.getNumber().value());
        cardRepository.save(cardDocument);
    }

    @Async
    @EventListener
    public void onTransactionAdded(TransactionAddedEvent event) {
        cardRepository.findByNumber(event.getCardNumber().value())
                .ifPresent(cardDocument -> addTransaction(event, cardDocument));
    }

    private void addTransaction(TransactionAddedEvent event, CardDocument cardDocument) {
        var transaction = event.getTransactionId().value().toString();
        cardDocument.addTransaction(transaction);
        cardRepository.save(cardDocument);
    }

}
