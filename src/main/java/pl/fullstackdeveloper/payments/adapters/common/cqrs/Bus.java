package pl.fullstackdeveloper.payments.adapters.common.cqrs;

public interface Bus {

    <R, C extends Command<R>> R execute(C command);

    <R, Q extends Query<R>> R execute(Q query);

}
