package pl.fullstackdeveloper.payments.internal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.fullstackdeveloper.payments.AddCardUseCase;
import pl.fullstackdeveloper.payments.AddTransactionUseCase;
import pl.fullstackdeveloper.payments.GetCardUseCase;
import pl.fullstackdeveloper.payments.GetCardsUseCase;
import pl.fullstackdeveloper.payments.infrastructure.events.TransactionEventPublisher;
import pl.fullstackdeveloper.payments.infrastructure.persistence.CardRepository;
import pl.fullstackdeveloper.payments.infrastructure.time.DateTimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCardUseCase addCardUseCase(CardNumberGenerator cardNumberGenerator, DateTimeProvider dateTimeProvider, CardRepository cardRepository) {
        return new AddCardUseCase(cardNumberGenerator, dateTimeProvider, cardRepository);
    }

    @Bean
    public GetCardsUseCase getCardsUseCase(CardRepository cardRepository) {
        return new GetCardsUseCase(cardRepository);
    }

    @Bean
    public GetCardUseCase getCardUseCase(CardRepository cardRepository) {
        return new GetCardUseCase(cardRepository);
    }

    @Bean
    public AddTransactionUseCase addTransactionUseCase(DateTimeProvider dateTimeProvider, TransactionEventPublisher transactionEventPublisher, CardRepository cardRepository) {
        return new AddTransactionUseCase(dateTimeProvider, transactionEventPublisher, cardRepository);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
