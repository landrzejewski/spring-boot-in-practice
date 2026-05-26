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

import java.math.BigDecimal;

/**
 * Symulowany serwis płatności. Trigger ścieżki kompensacji:
 * {@code amount > FAIL_THRESHOLD} powoduje publikację {@link StepResult.Failure}.
 */
@Component
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final BigDecimal FAIL_THRESHOLD = new BigDecimal("1000");

    private final ApplicationEventPublisher events;

    public PaymentService(ApplicationEventPublisher events) {
        this.events = events;
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.ProcessPayment cmd) {
        log.info("[{}] obciążam konto {} kwotą {}", Thread.currentThread().getName(),
                cmd.accountId(), cmd.amount());
        sleep(200);

        if (cmd.amount().compareTo(FAIL_THRESHOLD) > 0) {
            var reason = "limit konta przekroczony (amount=" + cmd.amount() + " > " + FAIL_THRESHOLD + ")";
            log.warn("[{}] płatność ODRZUCONA: {}", Thread.currentThread().getName(), reason);
            events.publishEvent(new StepResult.Failure(cmd.sagaId(), SagaStep.PROCESS_PAYMENT, reason));
            return;
        }

        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.PROCESS_PAYMENT));
    }

    @Async("sagaTaskExecutor")
    @EventListener
    public void on(SagaCommand.RefundPayment cmd) {
        log.info("[{}] KOMPENSACJA: refund {} dla konta {}", Thread.currentThread().getName(),
                cmd.amount(), cmd.accountId());
        sleep(150);
        events.publishEvent(new StepResult.Success(cmd.sagaId(), SagaStep.PROCESS_PAYMENT));
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
