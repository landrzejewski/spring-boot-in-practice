package pl.fullstackdeveloper.payments.adapters.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.adapters.common.JsonMapper;
import pl.fullstackdeveloper.payments.adapters.common.annotations.Mapper;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardId;
import pl.fullstackdeveloper.payments.domain.CardNumber;
import pl.fullstackdeveloper.payments.domain.Transaction;

import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static pl.fullstackdeveloper.common.Mappers.mapList;

@Mapper
final class JpaCardRepositoryMapper {

    private final static TypeReference<List<Transaction>> TRANSACTION_LIST_TYPE = new TypeReference<>() {
    };

    private final JsonMapper jsonMapper;

    JpaCardRepositoryMapper(final JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    CardEntity toEntity(final Card card) {
        var cardEntity = new CardEntity();
        cardEntity.setId(toEntity(card.getId()));
        cardEntity.setNumber(toEntity(card.getNumber()));
        cardEntity.setExpiration(card.getExpiration());
        cardEntity.setCurrencyCode(toEntity(card.getCurrency()));
        cardEntity.setTransactions(jsonMapper.write(card.getTransactions()));
        return cardEntity;
    }

    private String toEntity(final CardId cardId) {
        return cardId.value().toString();
    }

    private String toEntity(final Currency currency) {
        return currency.getCurrencyCode();
    }

    String toEntity(final CardNumber cardNumber) {
        return cardNumber.value();
    }

    PageRequest toEntity(final PageSpec pageSpec) {
        return PageRequest.of(pageSpec.index(), pageSpec.size());
    }

    Card toDomain(final CardEntity cardEntity) {
        var cardId = toDomain(cardEntity.getId());
        var cardNumber = new CardNumber(cardEntity.getNumber());
        var currency = Currency.getInstance(cardEntity.getCurrencyCode());
        var expiration = cardEntity.getExpiration();

        var card = new Card(cardId, cardNumber, expiration, currency);
        if (cardEntity.getTransactions() != null) {
            jsonMapper.read(cardEntity.getTransactions(), TRANSACTION_LIST_TYPE).forEach(card::registerTransaction);
        }
        return card;
    }

    private CardId toDomain(String id) {
        return new CardId(UUID.fromString(id));
    }

    ResultPage<Card> toDomain(final Page<CardEntity> page) {
        return new ResultPage<>(
                mapList(page.getContent(), this::toDomain),
                new PageSpec(page.getNumber(), page.getSize()),
                page.getTotalPages()
        );
    }

}
