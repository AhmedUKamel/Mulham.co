package org.ahmedukamel.mulham.service.db;

import org.springframework.security.authentication.LockedException;

import java.util.function.Supplier;

public class DatabaseChecker {
    public static void nonLocked(Supplier<Boolean> supplier) {
        if (supplier.get()) return;
        throw new LockedException("User account is locked.");
    }
}