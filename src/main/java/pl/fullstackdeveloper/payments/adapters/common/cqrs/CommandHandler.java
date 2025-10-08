package pl.fullstackdeveloper.payments.adapters.common.cqrs;

public interface CommandHandler<C extends Command> {

    void handle(C command);

}
