package de.breyer.aoc.y2022;

import java.util.concurrent.Callable;
import java.util.function.Function;

public record CallableWithParam<T, V>(T value, Function<T, V> function) implements Callable<V> {

    @Override
    public V call() {
        return function.apply(value);
    }
}
