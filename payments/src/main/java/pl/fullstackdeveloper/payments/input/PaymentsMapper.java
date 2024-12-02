package pl.fullstackdeveloper.payments.input;

import pl.fullstackdeveloper.common.ResultPage;
import pl.fullstackdeveloper.payments.CardInfo;
import pl.fullstackdeveloper.payments.domain.Card;
import pl.fullstackdeveloper.payments.domain.CardNumber;

public class PaymentsMapper {

    CardInfo map(Card card) {
        return new CardInfo(
                card.getId().value().toString(),
                card.getNumber().value(),
                card.getExpiration(),
                card.getCurrency(),
                card.getBalance()
        );
    }

    ResultPage<CardInfo> map(ResultPage<Card> cards) {
        return new ResultPage<>(
                cards.content().stream().map(this::map).toList(),
                cards.pageSpec(),
                cards.totalPages()
        );
    }

    CardNumber map(String number) {
        return new CardNumber(number);
    }

}
