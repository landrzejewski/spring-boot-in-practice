package pl.training.sagademo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.training.sagademo.saga.OrderSagaOrchestrator;
import pl.training.sagademo.saga.SagaInstanceRepository;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderSagaOrchestrator orchestrator;
    private final SagaInstanceRepository repository;

    public OrderController(OrderSagaOrchestrator orchestrator, SagaInstanceRepository repository) {
        this.orchestrator = orchestrator;
        this.repository = repository;
    }

    @PostMapping
    public Map<String, UUID> create(@RequestBody OrderRequest request) {
        var sagaId = orchestrator.start(
                request.productId(),
                request.quantity(),
                request.amount(),
                request.accountId(),
                request.address()
        );
        return Map.of("sagaId", sagaId);
    }

    @GetMapping("/{sagaId}")
    public ResponseEntity<SagaView> get(@PathVariable UUID sagaId) {
        return repository.findById(sagaId)
                .map(SagaView::of)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
