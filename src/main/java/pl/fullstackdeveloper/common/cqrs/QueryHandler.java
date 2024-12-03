package pl.fullstackdeveloper.common.cqrs;

public interface QueryHandler<R, C extends Query<R>> {
    R handle(C query);
}
