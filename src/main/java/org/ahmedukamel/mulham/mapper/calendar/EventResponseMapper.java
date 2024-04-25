package org.ahmedukamel.mulham.mapper.calendar;

import org.ahmedukamel.mulham.dto.calendar.EventResponse;
import org.ahmedukamel.mulham.model.Booking;
import org.ahmedukamel.mulham.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EventResponseMapper implements Function<Booking, EventResponse> {
    @Override
    public EventResponse apply(Booking booking) {
        User user = booking.getUser();
        EventResponse response = new EventResponse();
        response.setAddress(booking.getAddress());
        response.setStart(booking.getStart());
        response.setEnd(booking.getEnd());
        response.setService(booking.getService().getEnglishName());
        response.setUsername("%s %s".formatted(user.getFirstName(), user.getLastName()));
        return response;
    }
}