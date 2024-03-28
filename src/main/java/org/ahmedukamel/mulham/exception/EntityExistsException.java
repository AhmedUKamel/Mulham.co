package org.ahmedukamel.mulham.exception;

import lombok.Getter;

@Getter
public class EntityExistsException extends RuntimeException {
    private final String identifier;
    private final Class<?> theClass;

    public EntityExistsException(String identifier, Class<?> theClass) {
        super(theClass.getSimpleName() + " with " + identifier + " already exists.");
        this.identifier = identifier;
        this.theClass = theClass;
    }
}
