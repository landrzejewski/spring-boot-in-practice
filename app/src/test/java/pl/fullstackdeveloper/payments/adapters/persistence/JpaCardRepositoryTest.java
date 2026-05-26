package pl.fullstackdeveloper.payments.adapters.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace.NONE;
import static pl.fullstackdeveloper.payments.CardTestFixtures.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@ExtendWith(SpringExtension.class)
class JpaCardRepositoryTest {

    @Autowired
    private JpaCardRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void given_card_exists_when_find_by_id_should_return_card() {
        var cardEntity = createCardEntity();
        entityManager.persist(cardEntity);
        entityManager.flush();
        assertEquals(cardEntity, repository.findByNumber(cardEntity.getNumber()).orElseThrow());
    }

    private CardEntity createCardEntity() {
        var entity = new CardEntity();
        entity.setId(TEST_CARD_ID.value().toString());
        entity.setNumber(TEST_CARD_NUMBER.value());
        entity.setExpiration(TEST_EXPIRATION_DATE);
        entity.setCurrencyCode(TEST_CURRENCY.getCurrencyCode());
        return entity;
    }

}
