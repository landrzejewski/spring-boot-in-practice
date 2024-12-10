package pl.fullstackdeveloper.payments.adapters.persistence;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface JpaCardRepository extends Repository<CardEntity, String> {

    CardEntity save(CardEntity cardEntity);

    Optional<CardEntity> findByNumber(String cardNumber);

}
