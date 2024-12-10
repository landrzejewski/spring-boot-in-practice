package pl.fullstackdeveloper.payments.cqrs.usecases.getcards;

import java.util.List;

public interface GetCardsQueryResult {

    String getNumber();

    List<String> getTransactions();

}