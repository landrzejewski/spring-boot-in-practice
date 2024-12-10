package pl.fullstackdeveloper.payments.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface JpaCardRepository extends CrudRepository<CardEntity, String> {

    Page<CardEntity> findAll(Pageable pageable);

    Optional<CardEntity> findByNumber(String cardNumber);

}
