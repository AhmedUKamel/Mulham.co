package org.ahmedukamel.mulham.service.calendar;

import java.util.Optional;

public interface ICalendarService {
    Object getEvents(Optional<Integer> year, Optional<Integer> month, Optional<Integer> day);
}