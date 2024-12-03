package pl.fullstackdeveloper.orders.application;

import java.util.Currency;
import java.util.logging.Logger;

public class PlaceOrderUseCase {

    private static final Logger LOGGER = Logger.getLogger(PlaceOrderUseCase.class.getName());

    private final Payments payments;

    public PlaceOrderUseCase(Payments payments) {
        this.payments = payments;
    }

    public void handle(String orderDetails) {
        LOGGER.info("Placing order");
        // get paymentDetails based on customerId and orderDetails
        // get totalAmount from orderDetails
        var cardDetails = new PaymentDetails("7079327771560002");
        var transactionId = payments.pay(10.0, Currency.getInstance("PLN"), cardDetails);
    }

}
