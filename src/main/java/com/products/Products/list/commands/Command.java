package com.products.Products.list.commands;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Command<T> {
    T execute(CommandActions commandActions);

    default T execute(CommandExecutor commandExecutor) {
        return commandExecutor.execute(this);
    }

    default Command<T> onComplete(Runnable runnable) {
        return commandActions -> {
            T t = execute(commandActions);
            runnable.run();
            return t;
        };
    }

    default Command<T> onComplete(Consumer<T> runnable) {
        return commandActions -> {
            T t = execute(commandActions);
            runnable.accept(t);
            return t;
        };
    }

    default <S> Command<S> andThen(Function<T, Command<S>> andThen) {
        return commandActions -> {
            T t = execute(commandActions);
            return andThen.apply(t).execute(commandActions);
        };
    }

    default <S> Command<S> andThenMap(Function<T, S> andThen) {
        return commandActions -> {
            T t = execute(commandActions);
            return andThen.apply(t);
        };
    }
}
