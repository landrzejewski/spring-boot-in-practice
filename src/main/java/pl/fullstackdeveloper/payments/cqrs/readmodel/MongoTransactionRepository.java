package pl.fullstackdeveloper.payments.cqrs.readmodel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface MongoTransactionRepository extends MongoRepository<TransactionDocument, UUID> {

    List<TransactionDocument> findByCardNumberAndCurrency(String cardNumber, String currency);

}
