package pl.fullstackdeveloper.payments.adapters.common.cqrs;

import org.springframework.stereotype.Component;

@SuppressWarnings("ALL")
@Component
public class SpringBus implements Bus {

    private final Registry registry;

    public SpringBus(Registry registry) {
        this.registry = registry;
    }

    @Override
    public <R, C extends Command<R>> R execute(C command) {
        var commandHandler = (CommandHandler<R, C>) registry.getCmd(command.getClass());
        return commandHandler.handle(command);
    }

    @Override
    public <R, Q extends Query<R>> R execute(Q query) {
        var queryHandler = (QueryHandler<R, Q>) registry.getQuery(query.getClass());
        return queryHandler.handle(query);
    }

}
