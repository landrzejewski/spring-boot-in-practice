package pl.fullstackdeveloper.payments.cqrs.usecases.getcard;

import pl.fullstackdeveloper.payments.adapters.common.annotations.Handler;
import pl.fullstackdeveloper.payments.adapters.common.cqrs.QueryHandler;
import pl.fullstackdeveloper.payments.cqrs.readmodel.MongoTransactionRepository;
import pl.fullstackdeveloper.payments.cqrs.readmodel.TransactionDocument;

import java.math.BigDecimal;

@Handler
public class GetCardQueryHandler implements QueryHandler<GetCardResult, GetCardQuery> {

    private final MongoTransactionRepository transactionRepository;

    public GetCardQueryHandler(MongoTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public GetCardResult handle(GetCardQuery query) {
        var cardNumber = query.cardNumber();
        var totalBalance = transactionRepository.findByCardNumberAndCurrency(cardNumber, query.currencyCode())
                .stream()
                .map(TransactionDocument::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new GetCardResult(cardNumber, totalBalance);
    }

}
