package org.ahmedukamel.mulham.mapper.notification;

import org.ahmedukamel.mulham.dto.NotificationDto;
import org.ahmedukamel.mulham.model.Notification;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class NotificationDtoMapper implements Function<Notification, NotificationDto> {
    @Override
    public NotificationDto apply(Notification notification) {
        return new NotificationDto(
                notification.getId(),
                notification.getStart(),
                notification.getEnd(),
                notification.getType(),
                notification.getEnglishText(),
                notification.getArabicText()
        );
    }
}