package pl.fullstackdeveloper.payments.adapters.common.aop;

import jakarta.annotation.Nonnull;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.stream.Collectors.joining;

@Component
public final class CacheAspect implements MethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(CacheAspect.class.getName());

    private final Map<String, Object> cache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object invoke(@Nonnull final MethodInvocation invocation) throws Throwable {
        var key = generateKey(invocation);
        if (cache.containsKey(key)) {
            LOGGER.info("Reading from cache");
            return cache.get(key);
        }
        var result = invocation.proceed();
        cache.put(key, result);
        return result;
    }

    private String generateKey(final MethodInvocation invocation) {
        return Arrays.stream(invocation.getArguments())
                .map(Object::toString)
                .collect(joining());
    }

}
