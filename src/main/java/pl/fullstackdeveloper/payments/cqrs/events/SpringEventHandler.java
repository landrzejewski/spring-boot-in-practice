package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.payments.cqrs.readmodel.CardDocument;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoCardRepository;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoTransactionRepository;
import pl.fullstackdeveloper.payments.cqrs.readmodel.TransactionDocument;
import pl.fullstackdeveloper.payments.domain.Transaction;

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
        cardRepository.findByNumber(event.getCardNumber().value())
                .ifPresent(cardDocument -> addTransaction(event, cardDocument));
        var transactionDocument = toDocument(event);
        transactionRepository.save(transactionDocument);
    }

    private void addTransaction(TransactionAddedEvent event, CardDocument cardDocument) {
        var transaction = event.getTransaction().toString();
        cardDocument.addTransaction(transaction);
        cardRepository.save(cardDocument);
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
