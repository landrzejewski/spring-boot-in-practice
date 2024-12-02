package pl.fullstackdeveloper.payments;

public interface GetCardUseCase {

    CardInfo handle(String cardNumber);

}
