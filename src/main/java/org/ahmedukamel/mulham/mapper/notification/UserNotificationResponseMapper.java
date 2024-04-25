package org.ahmedukamel.mulham.mapper.notification;

import org.ahmedukamel.mulham.dto.response.UserNotificationResponse;
import org.ahmedukamel.mulham.model.UserNotification;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.Function;

@Component
public class UserNotificationResponseMapper implements Function<UserNotification, UserNotificationResponse> {
    final MessageSource messageSource;

    public UserNotificationResponseMapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public UserNotificationResponse apply(UserNotification userNotification) {
        String text = ContextHolderUtils.getLanguageCode()
                .equalsIgnoreCase("en") ? userNotification.getNotification().getEnglishText() :
                userNotification.getNotification().getArabicText();

        String typeName = userNotification.getNotification().getType().name();
        if (ContextHolderUtils.getLanguageCode().equalsIgnoreCase("ar")) {
            typeName = messageSource.getMessage("enumeration.NotificationType.%s".formatted(typeName), null, new Locale("ar"));
        }

        UserNotificationResponse response = new UserNotificationResponse();
        response.setId(userNotification.getNotification().getId());
        response.setStart(userNotification.getNotification().getStart());
        response.setEnd(userNotification.getNotification().getEnd());
        response.setTypeId(userNotification.getNotification().getType().getId());
        response.setRead(userNotification.isRead());
        response.setTypeName(typeName);
        response.setText(text);
        return response;
    }
}