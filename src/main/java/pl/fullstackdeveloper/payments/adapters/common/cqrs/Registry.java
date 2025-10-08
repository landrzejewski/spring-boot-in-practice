package pl.fullstackdeveloper.payments.adapters.common.cqrs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.GenericTypeResolver;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Configuration
public class Registry {

    private Map<Class<? extends Command>, CommandProvider> commandProviderMap = new HashMap<>();
    private Map<Class<? extends Query>, QueryProvider> queryProviderMap = new HashMap<>();

    public Registry(ApplicationContext applicationContext) {
        var names = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String name : names) {
            registerCommand(applicationContext, name);
        }
        names = applicationContext.getBeanNamesForType(QueryHandler.class);
        for (String name : names) {
            registerQuery(applicationContext, name);
        }
    }

    private void registerCommand(ApplicationContext applicationContext, String name) {
        Class<CommandHandler<?>> handlerClass = (Class<CommandHandler<?>>) applicationContext.getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, CommandHandler.class);
        Class<? extends Command> commandType = (Class<? extends Command>) generics[0];
        commandProviderMap.put(commandType, new CommandProvider(applicationContext, handlerClass));
    }

    private void registerQuery(ApplicationContext applicationContext, String name) {
        Class<QueryHandler<?, ?>> handlerClass = (Class<QueryHandler<?, ?>>) applicationContext.getType(name);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, QueryHandler.class);
        Class<? extends Query> queryType = (Class<? extends Query>) generics[1];
        queryProviderMap.put(queryType, new QueryProvider(applicationContext, handlerClass));
    }

    <R, C extends Command> CommandHandler<C> getCmd(Class<C> commandClass) {
        return commandProviderMap.get(commandClass).get();
    }

    <R, C extends Query<R>> QueryHandler<R, C> getQuery(Class<C> commandClass) {
        return queryProviderMap.get(commandClass).get();
    }

}
