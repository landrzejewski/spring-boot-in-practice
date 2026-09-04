package pl.fullstackdeveloper.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.fullstackdeveloper.orders.application.ConfirmOrderUseCase;
import pl.fullstackdeveloper.orders.application.Payments;
import pl.fullstackdeveloper.orders.application.PlaceOrderUseCase;

@Configuration
public class OrdersConfiguration {

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(Payments payments) {
        return new PlaceOrderUseCase(payments);
    }

    @Bean
    public ConfirmOrderUseCase confirmOrderUseCase() {
        return new ConfirmOrderUseCase();
    }

}
