package org.ahmedukamel.mulham.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserNotificationResponse {
    private Long id;

    private LocalDate start;

    private LocalDate end;

    private String typeName;

    private int typeId;

    private String text;

    private boolean read;
}