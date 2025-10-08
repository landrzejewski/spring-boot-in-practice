package pl.fullstackdeveloper.payments.adapters.common.cqrs;

public interface Bus {

    <C extends Command> void execute(C command);

    <R, Q extends Query<R>> R execute(Q query);

}
