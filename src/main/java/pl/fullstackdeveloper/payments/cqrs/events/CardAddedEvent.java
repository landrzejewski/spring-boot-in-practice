package pl.fullstackdeveloper.payments.cqrs.events;

import org.springframework.context.ApplicationEvent;
import pl.fullstackdeveloper.payments.domain.Card;

public class CardAddedEvent extends ApplicationEvent {

    private final Card card;

    public CardAddedEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

}
