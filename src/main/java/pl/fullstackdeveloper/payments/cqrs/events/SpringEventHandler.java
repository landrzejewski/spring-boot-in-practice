package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.payments.cqrs.queries.readmodel.CardDocument;
import pl.fullstackdeveloper.payments.cqrs.queries.readmodel.MongoCardRepository;
import pl.fullstackdeveloper.payments.cqrs.queries.readmodel.MongoTransactionRepository;
import pl.fullstackdeveloper.payments.cqrs.queries.readmodel.TransactionDocument;

@Component
public class SpringEventHandler {

    private final MongoCardRepository cardRepository;
    private final MongoTransactionRepository transactionRepository;

    public SpringEventHandler(MongoCardRepository cardRepository, MongoTransactionRepository transactionRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
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
        var transactionDocument = toDocument(event);
        transactionRepository.save(transactionDocument);
    }

    private TransactionDocument toDocument(TransactionAddedEvent event) {
        var transactionDocument = new TransactionDocument();
        transactionDocument.setTransactionId(event.getTransaction().id().value().toString());
        var value = event.getTransaction().value().amount();
        transactionDocument.setValue(
                switch (event.getTransaction().type()) {
                    case INFLOW -> value;
                    case PAYMENT -> value.negate();
                }
        );
        transactionDocument.setCurrency(event.getTransaction().value().currency().toString());
        transactionDocument.setCardNumber(event.getCardNumber().value());
        transactionDocument.setTimestamp(event.getTimestamp());
        return transactionDocument;
    }

}
