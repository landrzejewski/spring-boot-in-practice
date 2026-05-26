package pl.training.sagademo.web;

import pl.training.sagademo.saga.SagaInstance;
import pl.training.sagademo.saga.SagaStatus;
import pl.training.sagademo.saga.SagaStep;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record SagaView(
        UUID id,
        SagaStatus status,
        SagaStep currentStep,
        List<SagaStep> completedSteps,
        String failureReason,
        Instant startedAt,
        Instant updatedAt
) {
    public static SagaView of(SagaInstance saga) {
        return new SagaView(
                saga.getId(),
                saga.getStatus(),
                saga.getCurrentStep(),
                saga.getCompletedSteps(),
                saga.getFailureReason(),
                saga.getStartedAt(),
                saga.getUpdatedAt()
        );
    }
}
