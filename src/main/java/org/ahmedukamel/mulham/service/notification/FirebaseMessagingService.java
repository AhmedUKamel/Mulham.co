package org.ahmedukamel.mulham.service.notification;

import com.google.firebase.messaging.*;
import org.ahmedukamel.mulham.constant.ImageConstants;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.UserNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
public class FirebaseMessagingService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseMessagingService.class);
    final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void send(org.ahmedukamel.mulham.model.Notification notification) {
        Notification firebaseNotification = Notification
                .builder()
                .setTitle(titleOf(notification))
                .setBody(notification.getEnglishText())
                .setImage(ImageConstants.LIGHT_LOGO)
                .build();

        List<String> deviceTokens = notification.getUsers()
                .parallelStream()
                .map(UserNotification::getUser)
                .map(User::getDeviceTokens)
                .flatMap(Collection::stream)
                .toList();

        Function<String, Message> mapper = token -> Message
                .builder()
                .setToken(token)
                .setNotification(firebaseNotification)
                .build();

        List<Message> messages = deviceTokens
                .parallelStream()
                .map(mapper)
                .toList();

        try {
            BatchResponse response = firebaseMessaging.sendEach(messages, true);
            logger.info("Firebase Messaging Stats: %d success, %d failed".formatted(response.getSuccessCount(), response.getFailureCount()));
        } catch (FirebaseMessagingException exception) {
            logger.error("Firebase Messaging Failed: %s".formatted(exception.getMessage()));
        }
    }

    private String titleOf(org.ahmedukamel.mulham.model.Notification notification) {
        return switch (notification.getType()) {
            case ANNOUNCEMENT -> "Announcement";
            case DISCOUNT -> "Discount";
        };
    }
}