package org.ahmedukamel.mulham.service.db;

import org.ahmedukamel.mulham.exception.EntityNotFoundException;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DatabaseFetcher {
    public static <T, U, R> R get(BiFunction<T, U, Optional<R>> function, T value1, U value2, Class<?> theClass) {
        return function.apply(value1, value2)
                .orElseThrow(() -> new EntityNotFoundException(value1.toString() + " and " + value2.toString(), theClass));
    }

    public static <T, R> R get(Function<T, Optional<R>> function, T value, Class<?> theClass) {
        return function.apply(value)
                .orElseThrow(() -> new EntityNotFoundException(value.toString(), theClass));
    }
}