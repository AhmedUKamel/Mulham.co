package org.ahmedukamel.mulham.service.db;

import org.ahmedukamel.mulham.exception.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.LockedException;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DatabaseChecker {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseChecker.class);

    public static void nonLocked(Supplier<Boolean> supplier) {
        if (supplier.get()) return;
        throw new LockedException("User account is locked.");
    }

    public static <T> void unique(Predicate<T> predicate, T value, Class<?> theClass) {
        if (predicate.test(value)) {
            logger.error("%s with %s is already exist.".formatted(theClass.getSimpleName(), value));
            throw new EntityExistsException(value.toString(), theClass);
        }
    }

    public static <T, U> void unique(BiPredicate<T, U> predicate, T value1, U value2, Class<?> theClass) {
        if (predicate.test(value1, value2)) {
            logger.error("%s with %s is already exist.".formatted(theClass.getSimpleName(), value1));
            throw new EntityExistsException(value1.toString(), theClass);
        }
    }
}