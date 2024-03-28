package org.ahmedukamel.mulham.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Permission implements GrantedAuthority {
    READ_USER("user:read");

    private final String name;

    @Override
    public String getAuthority() {
        return this.name;
    }
}