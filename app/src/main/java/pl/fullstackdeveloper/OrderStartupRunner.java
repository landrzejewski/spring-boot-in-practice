package pl.fullstackdeveloper;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.orders.application.PlaceOrderUseCase;

@Component
class OrderStartupRunner implements ApplicationRunner {

    private final PlaceOrderUseCase placeOrderUseCase;

    OrderStartupRunner(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            placeOrderUseCase.handle("My order");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
