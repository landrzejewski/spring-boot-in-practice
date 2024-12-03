package pl.fullstackdeveloper.payments.cqrs.getcard;

import pl.fullstackdeveloper.common.cqrs.Query;

public class GetCardQuery implements Query<CardProjection> {

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}
