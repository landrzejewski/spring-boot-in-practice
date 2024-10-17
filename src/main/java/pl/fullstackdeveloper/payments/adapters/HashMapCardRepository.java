package pl.fullstackdeveloper.payments.adapters;

import pl.fullstackdeveloper.common.PageSpec;
import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.application.CardRepository;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapCardRepository implements CardRepository {

    private final Map<CardNumber, Card> data = new HashMap<>();

    @Override
    public synchronized Card save(final Card card) {
        data.put(card.getNumber(), card);
        return card;
    }

    @Override
    public synchronized ResultPage<Card> findAll(final PageSpec pageSpec) {
        var cards = new ArrayList<>(data.values());
        int startIndex = pageSpec.index() * pageSpec.size();
        int endIndex = Math.min(startIndex + pageSpec.size(), cards.size());
        var content = cards.subList(startIndex, endIndex);
        int totalPages = (int) Math.ceil((double) cards.size() / pageSpec.size());
        return new ResultPage<>(content, pageSpec, totalPages);
    }

    @Override
    public synchronized Optional<Card> findByNumber(final CardNumber cardNumber) {
        var card = data.get(cardNumber);
        return Optional.ofNullable(card);
    }

}
