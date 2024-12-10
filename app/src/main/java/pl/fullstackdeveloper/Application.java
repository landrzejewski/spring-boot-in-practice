package pl.fullstackdeveloper;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.fullstackdeveloper.orders.application.PlaceOrderUseCase;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private final PlaceOrderUseCase placeOrderUseCase;

    public Application(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
