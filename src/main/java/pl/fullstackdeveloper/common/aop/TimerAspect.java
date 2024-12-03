package pl.fullstackdeveloper.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import pl.fullstackdeveloper.common.annotations.Timer;

import java.util.logging.Logger;

import static pl.fullstackdeveloper.common.annotations.Timer.TimeUnit.NS;

// @Order(100_000)
@Aspect
@Component
public final class TimerAspect implements Ordered {

    private static final Logger LOGGER = Logger.getLogger(TimerAspect.class.getName());

    @Around("@annotation(timer)")
    public Object measure(final ProceedingJoinPoint joinPoint, final Timer timer) throws Throwable {
        var timeUnit = timer.value();
        var startTime = getTime(timeUnit);
        var result = joinPoint.proceed();
        var endTime = getTime(timeUnit);
        var totalTime = endTime - startTime;
        LOGGER.info("Method %s executes in %d %s".formatted(joinPoint.getSignature(), totalTime, timeUnit.name().toLowerCase()));
        return result;
    }

    private long getTime(final Timer.TimeUnit timeUnit) {
        return timeUnit == NS ? System.nanoTime() : System.currentTimeMillis();
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
