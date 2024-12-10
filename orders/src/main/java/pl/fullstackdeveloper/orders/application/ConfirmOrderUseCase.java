package pl.fullstackdeveloper.orders.application;

import java.util.logging.Logger;

public class ConfirmOrderUseCase {

    private static final Logger LOGGER = Logger.getLogger(ConfirmOrderUseCase.class.getName());

    public void handle(String transactionId) {
        LOGGER.info("Confirming order payment with transaction id " + transactionId);
    }

}
