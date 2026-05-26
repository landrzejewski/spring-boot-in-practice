package pl.training.sagademo.saga;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Persystowany stan sagi. W realnym systemie pozwala wznowić sagę po restarcie /
 * awarii orchestratora. Nie używamy {@code record}, bo JPA wymaga konstruktora
 * bez argumentów i mutowalnych pól zarządzanych przez Hibernate.
 */
@Entity
@Table(name = "saga_instance")
public class SagaInstance {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    @Enumerated(EnumType.STRING)
    private SagaStep currentStep;

    private String productId;
    private int quantity;
    private BigDecimal amount;
    private String accountId;
    private String address;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "saga_completed_steps", joinColumns = @JoinColumn(name = "saga_id"))
    @OrderColumn(name = "step_order")
    @Enumerated(EnumType.STRING)
    @Column(name = "step")
    private List<SagaStep> completedSteps = new ArrayList<>();

    private String failureReason;

    private Instant startedAt;
    private Instant updatedAt;

    protected SagaInstance() {
    }

    public SagaInstance(UUID id, String productId, int quantity, BigDecimal amount,
                        String accountId, String address) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.accountId = accountId;
        this.address = address;
        this.status = SagaStatus.STARTED;
        this.currentStep = SagaStep.RESERVE_INVENTORY;
        this.startedAt = Instant.now();
        this.updatedAt = this.startedAt;
    }

    public void markStepCompleted(SagaStep step) {
        completedSteps.add(step);
        touch();
    }

    public SagaStep popLastCompleted() {
        if (completedSteps.isEmpty()) {
            return null;
        }
        var last = completedSteps.removeLast();
        touch();
        return last;
    }

    public SagaStep peekLastCompleted() {
        return completedSteps.isEmpty() ? null : completedSteps.getLast();
    }

    public void advanceTo(SagaStep next) {
        this.currentStep = next;
        touch();
    }

    public void markCompleted() {
        this.status = SagaStatus.COMPLETED;
        this.currentStep = null;
        touch();
    }

    public void markCompensating(String reason) {
        this.status = SagaStatus.COMPENSATING;
        this.failureReason = reason;
        touch();
    }

    public void markCompensated() {
        this.status = SagaStatus.COMPENSATED;
        this.currentStep = null;
        touch();
    }

    public void markFailed(String reason) {
        this.status = SagaStatus.FAILED;
        this.failureReason = reason;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public SagaStatus getStatus() { return status; }
    public SagaStep getCurrentStep() { return currentStep; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getAmount() { return amount; }
    public String getAccountId() { return accountId; }
    public String getAddress() { return address; }
    public List<SagaStep> getCompletedSteps() { return List.copyOf(completedSteps); }
    public String getFailureReason() { return failureReason; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
