package pl.fullstackdeveloper.payments.adapters.persistence;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Adapter;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.Optional;

@Adapter
final class JpaCardRepositoryAdapter implements CardRepository {

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
