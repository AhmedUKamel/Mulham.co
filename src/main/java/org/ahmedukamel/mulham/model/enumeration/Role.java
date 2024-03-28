package org.ahmedukamel.mulham.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Set;

import static org.ahmedukamel.mulham.model.enumeration.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of()),

    ADMIN(Set.of(READ_USER)),

    SUPER_ADMIN(Set.of(READ_USER));

    private final Collection<Permission> permissions;
}