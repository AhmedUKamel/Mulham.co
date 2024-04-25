package org.ahmedukamel.mulham.service.calendar;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.calendar.EventResponse;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.mapper.calendar.EventResponseMapper;
import org.ahmedukamel.mulham.model.Booking;
import org.ahmedukamel.mulham.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService implements ICalendarService {
    final BookingRepository repository;
    final EventResponseMapper mapper;

    @Override
    public Object getEvents(Optional<Integer> year, Optional<Integer> month, Optional<Integer> day) {
        final List<Booking> bookings;
        if (year.isPresent()) {
            if (month.isPresent()) {
                if (day.isPresent()) {
                    bookings = repository.findByDate(year.get(), month.get(), day.get());
                } else {
                    bookings = repository.findByDate(year.get(), month.get());
                }
            } else {
                bookings = repository.findByDate(year.get());
            }
        } else {
            bookings = repository.findAll();
        }
        List<EventResponse> response = bookings
                .stream()
                .map(mapper)
                .toList();
        return new ApiResponse(true, "Events", response);
    }
}