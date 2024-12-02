package pl.fullstackdeveloper.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.fullstackdeveloper.payments.application.AddCardService;
import pl.fullstackdeveloper.payments.application.AddTransactionService;
import pl.fullstackdeveloper.payments.application.GetCardService;
import pl.fullstackdeveloper.payments.application.GetCardsService;
import pl.fullstackdeveloper.payments.input.*;
import pl.fullstackdeveloper.payments.output.CardNumberGenerator;
import pl.fullstackdeveloper.payments.output.CardRepository;
import pl.fullstackdeveloper.payments.output.DateTimeProvider;
import pl.fullstackdeveloper.payments.output.TransactionEventPublisher;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public PaymentsMapper paymentsMapper() {
        return new PaymentsMapper();
    }

    @Bean
    public AddCardUseCase addCardUseCase(CardNumberGenerator cardNumberGenerator, DateTimeProvider dateTimeProvider, CardRepository cardRepository) {
        return new AddCardServiceAdapter(new AddCardService(cardNumberGenerator, dateTimeProvider, cardRepository));
    }

    @Bean
    public GetCardsUseCase getCardsUseCase(CardRepository cardRepository, PaymentsMapper paymentsMapper) {
        return new GetCardsServiceAdapter(new GetCardsService(cardRepository), paymentsMapper);
    }

    @Bean
    public GetCardUseCase getCardUseCase(CardRepository cardRepository, PaymentsMapper paymentsMapper) {
        return new GetCardServiceAdapter(new GetCardService(cardRepository), paymentsMapper);
    }

    @Bean
    public AddCardTransactionUseCase addTransactionUseCase(DateTimeProvider dateTimeProvider, TransactionEventPublisher transactionEventPublisher, CardRepository cardRepository, PaymentsMapper paymentsMapper) {
        return new AddCardTransactionServiceAdapter(new AddTransactionService(dateTimeProvider, transactionEventPublisher, cardRepository), paymentsMapper);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
