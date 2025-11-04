package pl.fullstackdeveloper.payments.infrastructure.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import pl.fullstackdeveloper.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.TransactionAdded;

import java.util.logging.Logger;

@Primary
@Adapter
final class SpringTransactionEventPublisher implements TransactionEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(SpringTransactionEventPublisher.class.getName());

    private final ApplicationEventPublisher eventPublisher;

    public SpringTransactionEventPublisher(final ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(final TransactionAdded transactionAdded) {
        LOGGER.info("### Publishing transaction event");
        eventPublisher.publishEvent(transactionAdded);
    }

}
