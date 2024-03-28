package org.ahmedukamel.mulham.util;

import jakarta.persistence.EntityNotFoundException;
import org.ahmedukamel.mulham.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ContextHolderUtils {
    public static Object getPrinciple() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Optional<User> getUser() {
        if (getPrinciple() instanceof User user)
            return Optional.of(user);
        return Optional.empty();
    }

    public static User getUserOrElseThrow() {
        return getUser().orElseThrow(EntityNotFoundException::new);
    }
}