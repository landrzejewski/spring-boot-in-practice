package pl.fullstackdeveloper.payments.cqrs.queries.getcards;

import java.util.List;

public interface GetCardsQueryResult {

    String getNumber();

    List<String> getTransactions();

}