package pl.training.sagademo.saga;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Zamknięta hierarchia komend sagi. Każdy permit to nieprzemienny {@code record}
 * publikowany przez orchestratora przez {@code ApplicationEventPublisher} i odbierany
 * przez właściwy serwis (Inventory / Payment / Shipping) za pomocą {@code @EventListener}.
 *
 * Sealed pozwala wymusić wyczerpujące dopasowanie w {@code switch} — dodanie nowego
 * permita spowoduje błąd kompilacji w miejscach, które trzeba zaktualizować.
 */
public sealed interface SagaCommand {

    UUID sagaId();

    record ReserveInventory(UUID sagaId, String productId, int quantity) implements SagaCommand {}

    record ProcessPayment(UUID sagaId, String accountId, BigDecimal amount) implements SagaCommand {}

    record ShipOrder(UUID sagaId, String address) implements SagaCommand {}

    record ReleaseInventory(UUID sagaId, String productId, int quantity) implements SagaCommand {}

    record RefundPayment(UUID sagaId, String accountId, BigDecimal amount) implements SagaCommand {}

    record CancelShipment(UUID sagaId, String address) implements SagaCommand {}
}
