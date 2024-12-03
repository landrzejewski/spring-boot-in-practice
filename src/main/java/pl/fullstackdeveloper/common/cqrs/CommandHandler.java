package pl.fullstackdeveloper.common.cqrs;

public interface CommandHandler<R, C extends Command<R>> {
    R handle(C command);
}
