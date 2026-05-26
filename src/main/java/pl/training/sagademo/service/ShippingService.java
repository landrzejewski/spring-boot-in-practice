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

@Component
public class ShippingService {

    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);

    private final ApplicationEventPublisher events;

    public ShippingService(ApplicationEventPublisher events) {
        this.events = events;
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.ShipOrder cmd) {
        log.info("[{}] zlecam wysyłkę na adres: {}", Thread.currentThread().getName(), cmd.address());
        sleep(150);
        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.SHIP_ORDER));
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.CancelShipment cmd) {
        log.info("[{}] KOMPENSACJA: anuluję wysyłkę na adres: {}", Thread.currentThread().getName(),
                cmd.address());
        sleep(150);
        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.SHIP_ORDER));
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
