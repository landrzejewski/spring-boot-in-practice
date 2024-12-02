package pl.fullstackdeveloper.payments.adapters.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.common.annotations.MinLength;

import static pl.fullstackdeveloper.payments.adapters.common.aop.Aop.applyArgumentOperator;

@Aspect
@Component
public final class MinLengthAspect {

    @Before("execution(* *(@pl.fullstackdeveloper.common.annotations.MinLength (*)))")
    public void validate(final JoinPoint joinPoint) throws NoSuchMethodException {
        applyArgumentOperator(joinPoint, MinLength.class, (String argument, MinLength minLength) -> {
            if (argument.length() < minLength.value()) {
                throw new IllegalArgumentException("Value is too short, minimum length is: " + minLength.value());
            }
        });
    }

}
