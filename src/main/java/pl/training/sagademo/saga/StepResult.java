package pl.training.sagademo.saga;

import java.util.UUID;

/**
 * Odpowiedź serwisu po wykonaniu kroku sagi. Sealed + dwa rekordy ({@code Success},
 * {@code Failure}) — orchestrator obsługuje je w jednym {@code switch}, kompilator
 * pilnuje wyczerpującego pokrycia.
 */
public sealed interface StepResult {

    UUID sagaId();

    SagaStep step();

    record Success(UUID sagaId, SagaStep step) implements StepResult {}

    record Failure(UUID sagaId, SagaStep step, String reason) implements StepResult {}
}
