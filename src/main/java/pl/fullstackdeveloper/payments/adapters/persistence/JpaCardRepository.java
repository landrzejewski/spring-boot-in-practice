package pl.fullstackdeveloper.payments.adapters.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import pl.fullstackdeveloper.payments.cqrs.getcard.CardProjection;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Optional;

interface JpaCardRepository extends CrudRepository<CardEntity, String> {

    Page<CardEntity> findAll(Pageable pageable);

    Optional<CardEntity> findByNumber(String cardNumber);

    Optional<CardProjection> findProjectionByNumber(String cardNumber);

}
