package pl.fullstackdeveloper.payments.cqrs.queries.readmodel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.fullstackdeveloper.payments.cqrs.queries.getcards.GetCardsQueryResult;

import java.util.Optional;
import java.util.UUID;

public interface MongoCardRepository extends MongoRepository<CardDocument, UUID> {

    Optional<CardDocument> findByNumber(String cardNumber);

    @Query("select from CardDocument")
    Page<GetCardsQueryResult> findAllQueryResults(Pageable pageable);

}
