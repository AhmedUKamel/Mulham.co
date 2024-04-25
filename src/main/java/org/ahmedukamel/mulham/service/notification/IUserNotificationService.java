package org.ahmedukamel.mulham.service.notification;

import org.ahmedukamel.mulham.model.enumeration.NotificationType;

public interface IUserNotificationService {
    Object getUnreadNotificationsCount();

    Object getNotifications();

    Object getNotifications(long pageSize, long pageNumber);

    Object getNotifications(NotificationType type);

    Object getNotifications(NotificationType type, long pageSize, long pageNumber);

    Object readNotification(Long notificationId);

    void deleteNotification(Long notificationId);
}