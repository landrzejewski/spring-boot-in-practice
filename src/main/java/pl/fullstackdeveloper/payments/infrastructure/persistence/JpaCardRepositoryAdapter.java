package pl.fullstackdeveloper.payments.infrastructure.persistence;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.fullstackdeveloper.common.model.PageSpec;
import pl.fullstackdeveloper.common.model.ResultPage;
import pl.fullstackdeveloper.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.Card;
import pl.fullstackdeveloper.payments.CardNumber;

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
    public ResultPage<Card> findAll(final PageSpec pageSpec) {
        var pageRequest = mapper.toEntity(pageSpec);
        var cardEntityPage = repository.findAll(pageRequest);
        return mapper.toDomain(cardEntityPage);
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
