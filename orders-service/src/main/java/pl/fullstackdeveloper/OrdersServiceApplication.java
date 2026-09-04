package pl.fullstackdeveloper;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.fullstackdeveloper.orders.application.PlaceOrderUseCase;

@SpringBootApplication
public class OrdersServiceApplication implements ApplicationRunner {

    private final PlaceOrderUseCase placeOrderUseCase;

    public OrdersServiceApplication(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrdersServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        placeOrderUseCase.handle("Pizza");
    }

}
