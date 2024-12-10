package pl.fullstackdeveloper.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.fullstackdeveloper.payments.application.*;

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