package org.ahmedukamel.mulham.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final String identifier;
    private final Class<?> theClass;

    public EntityNotFoundException(String identifier, Class<?> theClass) {
        super(theClass.getSimpleName() + " with " + identifier + " not found.");
        this.identifier = identifier;
        this.theClass = theClass;
    }
}
