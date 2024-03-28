package org.ahmedukamel.mulham.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    NOT_SELECTED("Not selected");

    private final String name;
}