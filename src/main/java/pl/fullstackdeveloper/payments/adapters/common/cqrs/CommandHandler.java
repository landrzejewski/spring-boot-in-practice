package pl.fullstackdeveloper.payments.adapters.common.cqrs;

public interface CommandHandler<R, C extends Command<R>> {

    R handle(C command);

}
