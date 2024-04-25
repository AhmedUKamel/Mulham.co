package org.ahmedukamel.mulham.dto.calendar;

import lombok.Data;
import org.ahmedukamel.mulham.model.Address;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private LocalDateTime start;

    private LocalDateTime end;

    private Address address;

    private String service;

    private String username;
}