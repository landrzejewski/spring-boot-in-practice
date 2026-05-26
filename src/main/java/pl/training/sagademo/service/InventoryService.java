package pl.training.sagademo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import pl.training.sagademo.saga.SagaCommand;
import pl.training.sagademo.saga.SagaStep;
import pl.training.sagademo.saga.StepResult;

/**
 * Symuluje zewnętrzny serwis magazynowy. @Async + @EventListener gwarantują,
 * że handler odpala się w wątku puli "saga-", nie blokując orchestratora ani HTTP.
 *
 * W realnym systemie {@code ApplicationEventPublisher} zastąpiłby producent Kafki/Rabbita,
 * a metoda byłaby konsumentem tematu.
 */
@Component
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final ApplicationEventPublisher events;

    public InventoryService(ApplicationEventPublisher events) {
        this.events = events;
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.ReserveInventory cmd) {
        log.info("[{}] rezerwuję {} szt. produktu {}", Thread.currentThread().getName(),
                cmd.quantity(), cmd.productId());
        sleep(150);
        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.RESERVE_INVENTORY));
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.ReleaseInventory cmd) {
        log.info("[{}] KOMPENSACJA: zwalniam {} szt. produktu {}", Thread.currentThread().getName(),
                cmd.quantity(), cmd.productId());
        sleep(150);
        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.RESERVE_INVENTORY));
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
