package pl.fullstackdeveloper.payments.adapters.common.cqrs;

public interface QueryHandler<R, C extends Query<R>> {

    R handle(C query);

}
