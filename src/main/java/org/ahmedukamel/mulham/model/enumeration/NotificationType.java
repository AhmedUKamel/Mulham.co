package org.ahmedukamel.mulham.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    ANNOUNCEMENT(1),
    DISCOUNT(2);
    private final int id;
}