package org.ahmedukamel.mulham.service.notification;

public interface INotificationManagementService {
    Object createNotification(Object object);

    Object updateNotification(Long id, Object object);

    Object getNotification(Long id);

    Object getNotifications();

    Object getNotifications(long pageSize, long pageNumber);

    void deleteNotification(Long id);
}