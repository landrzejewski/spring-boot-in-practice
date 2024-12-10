package pl.fullstackdeveloper.payments.adapters.persistence;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Optional;

@Transactional(propagation = Propagation.MANDATORY)
@Adapter
 class JpaCardRepositoryAdapter implements CardRepository {

    private final JpaCardRepository repository;
    private final JpaCardRepositoryMapper mapper;

    JpaCardRepositoryAdapter(final JpaCardRepository repository, final JpaCardRepositoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Card> findByNumber(final CardNumber cardNumber) {
        var number = mapper.toEntity(cardNumber);
        return repository.findByNumber(number).map(mapper::toDomain);
    }

    @Override
    public Card save(final Card card) {
        var cardEntity = mapper.toEntity(card);
        var savedCardEntity = repository.save(cardEntity);
        return mapper.toDomain(savedCardEntity);
    }

}
