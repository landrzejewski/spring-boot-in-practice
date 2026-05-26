package pl.training.sagademo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Centralny koordynator sagi (wariant orchestration). Decyduje:
 *  - który krok wystartować jako pierwszy,
 *  - co zrobić po sukcesie/porażce kroku,
 *  - jakie kompensacje (w odwrotnej kolejności do ukończonych kroków) wykonać.
 *
 * Komunikacja "do" serwisów: {@link ApplicationEventPublisher} (komendy {@link SagaCommand}).
 * Komunikacja "od" serwisów: {@link StepResult} przychodzi przez {@link EventListener}.
 *
 * W realnym systemie zdarzenia trafiałyby na brokera (Kafka/Rabbit) — sealed/records
 * i logika orchestratora pozostałyby takie same.
 */
@Component
public class OrderSagaOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(OrderSagaOrchestrator.class);

    private final ApplicationEventPublisher events;
    private final SagaInstanceRepository repository;

    public OrderSagaOrchestrator(ApplicationEventPublisher events, SagaInstanceRepository repository) {
        this.events = events;
        this.repository = repository;
    }

    @Transactional
    public UUID start(String productId, int quantity, BigDecimal amount, String accountId, String address) {
        var saga = new SagaInstance(UUID.randomUUID(), productId, quantity, amount, accountId, address);
        repository.save(saga);
        log.info("Saga {} STARTED", saga.getId());
        dispatch(commandFor(SagaStep.RESERVE_INVENTORY, saga));
        return saga.getId();
    }

    @EventListener
    @Transactional
    public void onStepResult(StepResult result) {
        var saga = repository.findById(result.sagaId()).orElseThrow();

        switch (result) {
            case StepResult.Success success -> handleSuccess(saga, success);
            case StepResult.Failure failure -> handleFailure(saga, failure);
        }
    }

    private void handleSuccess(SagaInstance saga, StepResult.Success success) {
        if (saga.getStatus() == SagaStatus.COMPENSATING) {
            log.info("Saga {} kompensacja {} OK", saga.getId(), success.step());
            saga.popLastCompleted();
            continueOrFinishCompensation(saga);
            return;
        }

        saga.markStepCompleted(success.step());
        log.info("Saga {} krok {} OK", saga.getId(), success.step());

        var next = nextStepAfter(success.step());
        if (next == null) {
            saga.markCompleted();
            repository.save(saga);
            log.info("Saga {} COMPLETED", saga.getId());
            return;
        }

        saga.advanceTo(next);
        repository.save(saga);
        dispatch(commandFor(next, saga));
    }

    private void handleFailure(SagaInstance saga, StepResult.Failure failure) {
        log.warn("Saga {} krok {} FAILED: {}", saga.getId(), failure.step(), failure.reason());

        if (saga.getStatus() == SagaStatus.COMPENSATING) {
            // porażka w trakcie kompensacji — w realu wymaga alertu / retry / DLQ
            saga.markFailed("kompensacja nieudana: " + failure.reason());
            repository.save(saga);
            log.error("Saga {} FAILED w trakcie kompensacji ({})", saga.getId(), failure.step());
            return;
        }

        saga.markCompensating(failure.reason());
        repository.save(saga);
        continueOrFinishCompensation(saga);
    }

    private void continueOrFinishCompensation(SagaInstance saga) {
        var next = saga.peekLastCompleted();
        if (next == null) {
            saga.markCompensated();
            repository.save(saga);
            log.info("Saga {} COMPENSATED", saga.getId());
            return;
        }
        repository.save(saga);
        dispatch(compensationFor(next, saga));
    }

    private SagaStep nextStepAfter(SagaStep current) {
        return switch (current) {
            case RESERVE_INVENTORY -> SagaStep.PROCESS_PAYMENT;
            case PROCESS_PAYMENT -> SagaStep.SHIP_ORDER;
            case SHIP_ORDER -> null;
        };
    }

    private SagaCommand commandFor(SagaStep step, SagaInstance saga) {
        return switch (step) {
            case RESERVE_INVENTORY ->
                    new SagaCommand.ReserveInventory(saga.getId(), saga.getProductId(), saga.getQuantity());
            case PROCESS_PAYMENT ->
                    new SagaCommand.ProcessPayment(saga.getId(), saga.getAccountId(), saga.getAmount());
            case SHIP_ORDER ->
                    new SagaCommand.ShipOrder(saga.getId(), saga.getAddress());
        };
    }

    private SagaCommand compensationFor(SagaStep step, SagaInstance saga) {
        return switch (step) {
            case RESERVE_INVENTORY ->
                    new SagaCommand.ReleaseInventory(saga.getId(), saga.getProductId(), saga.getQuantity());
            case PROCESS_PAYMENT ->
                    new SagaCommand.RefundPayment(saga.getId(), saga.getAccountId(), saga.getAmount());
            case SHIP_ORDER ->
                    new SagaCommand.CancelShipment(saga.getId(), saga.getAddress());
        };
    }

    private void dispatch(SagaCommand command) {
        log.info("→ dispatch {}", command.getClass().getSimpleName());
        events.publishEvent(command);
    }
}
