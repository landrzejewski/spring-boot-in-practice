package pl.fullstackdeveloper.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public final class Aop {

    public static <T extends Annotation> T getClassAnnotation(final ProceedingJoinPoint joinPoint, final Class<T> type) {
        return joinPoint.getTarget().getClass().getAnnotation(type);
    }

    public static <T extends Annotation> T getMethodAnnotation(final ProceedingJoinPoint joinPoint, final Class<T> type) throws NoSuchMethodException {
        return getTargetMethod(joinPoint).getAnnotation(type);
    }

    public static Method getTargetMethod(final JoinPoint joinPoint) throws NoSuchMethodException {
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        var parameterTypes = signature.getMethod().getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
    }

    public static <T extends Annotation> T findAnnotation(final ProceedingJoinPoint joinPoint, final Class<T> type) throws NoSuchMethodException {
        var annotation = getMethodAnnotation(joinPoint, type);
        return annotation != null ? annotation : getClassAnnotation(joinPoint, type);
    }

    public static <T extends Annotation> Optional<T> findAnnotation(final Annotation[] annotations, final Class<T> type) {
        return Arrays.stream(annotations)
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    public static <P, A extends Annotation> void applyArgumentOperator(final JoinPoint joinPoint, final Class<A> annotationType, final ArgumentOperator<P, A> argumentOperator) throws NoSuchMethodException {
        var arguments = joinPoint.getArgs();
        var argumentsAnnotations = getTargetMethod(joinPoint).getParameterAnnotations();
        for (int index = 0; index < arguments.length; index++) {
            var argument = (P) arguments[index];
            findAnnotation(argumentsAnnotations[index], annotationType)
                    .ifPresent(annotation -> argumentOperator.apply(argument, annotation));
        }
    }

    public interface ArgumentOperator<P, A> {

        void apply(P parameterType, A annotationType);

    }

}
