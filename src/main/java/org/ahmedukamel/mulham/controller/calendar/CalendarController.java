package org.ahmedukamel.mulham.controller.calendar;

import org.ahmedukamel.mulham.service.calendar.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@PreAuthorize(value = "hasAuthority('ADMIN')")
@RequestMapping(value = "api/v1/calendar")
public class CalendarController {
    final ICalendarService service;

    public CalendarController(CalendarService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getCalendar(@RequestParam(value = "year", required = false) Optional<Integer> year,
                                         @RequestParam(value = "month", required = false) Optional<Integer> month,
                                         @RequestParam(value = "day", required = false) Optional<Integer> day) {
        return ResponseEntity.ok().body(service.getEvents(year, month, day));
    }
}