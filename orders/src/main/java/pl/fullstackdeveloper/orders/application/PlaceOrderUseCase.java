package pl.fullstackdeveloper.orders.application;

import java.util.Currency;
import java.util.logging.Logger;

public class PlaceOrderUseCase {

    private static final Logger LOGGER = Logger.getLogger(PlaceOrderUseCase.class.getName());

    private final PaymentsApi paymentsApi;

    public PlaceOrderUseCase(PaymentsApi paymentsApi) {
        this.paymentsApi = paymentsApi;
    }

    public void handle(String orderDetails) {
        LOGGER.info("Placing order");
        // get paymentDetails based on customerId and orderDetails
        // get totalAmount from orderDetails
        var cardDetails = new PaymentDetails("");
        var transactionId = paymentsApi.pay(1_000.0, Currency.getInstance("pl"), cardDetails);
    }

}
