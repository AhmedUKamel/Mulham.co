package org.ahmedukamel.mulham.service.notification;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.dto.response.UserNotificationResponse;
import org.ahmedukamel.mulham.exception.EntityNotFoundException;
import org.ahmedukamel.mulham.mapper.notification.UserNotificationResponseMapper;
import org.ahmedukamel.mulham.model.Notification;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.UserNotification;
import org.ahmedukamel.mulham.model.enumeration.NotificationType;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.util.ContextHolderUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

@Service
@RequiredArgsConstructor
public class UserNotificationService implements IUserNotificationService {
    final UserNotificationResponseMapper mapper;
    final UserRepository repository;

    private static final BiPredicate<Notification, NotificationType> typePredicate =
            (notification, type) -> notification.getType().equals(type);
    private static final BiFunction<User, Long, UserNotification> userNotificationFunction =
            (user, id) -> user.getNotifications()
                    .stream()
                    .filter(i -> i.getNotification().getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(id.toString(), Notification.class));

    private static final ToLongFunction<UserNotification> notificationId =
            userNotification -> userNotification.getNotification().getId();

    @Override
    public Object getUnreadNotificationsCount() {
        Predicate<UserNotification> predicate = userNotification -> !userNotification.isRead();
        long count = ContextHolderUtils.getUserOrElseThrow()
                .getNotifications()
                .stream()
                .filter(predicate)
                .count();

        return new ApiResponse(true, "Unread notifications count!", count);
    }

    @Override
    public Object getNotifications() {
        List<UserNotificationResponse> responses = ContextHolderUtils.getUserOrElseThrow()
                .getNotifications()
                .stream()
                .sorted(Comparator.comparingLong(notificationId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "All notifications returned successfully!", responses);
    }

    @Override
    public Object getNotifications(long pageSize, long pageNumber) {
        List<UserNotificationResponse> responses = ContextHolderUtils.getUserOrElseThrow()
                .getNotifications()
                .stream()
                .sorted(Comparator.comparingLong(notificationId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "Notifications returned successfully!", responses);
    }

    @Override
    public Object getNotifications(NotificationType type) {
        List<UserNotificationResponse> responses = ContextHolderUtils.getUserOrElseThrow()
                .getNotifications()
                .stream()
                .filter(userNotification -> typePredicate.test(userNotification.getNotification(), type))
                .sorted(Comparator.comparingLong(notificationId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "%s notifications returned successfully!".formatted(type), responses);
    }

    @Override
    public Object getNotifications(NotificationType type, long pageSize, long pageNumber) {
        List<UserNotificationResponse> responses = ContextHolderUtils.getUserOrElseThrow()
                .getNotifications()
                .stream()
                .filter(userNotification -> typePredicate.test(userNotification.getNotification(), type))
                .sorted(Comparator.comparingLong(notificationId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "%s notifications returned successfully!".formatted(type), responses);
    }

    @Override
    public Object readNotification(Long notificationId) {
        User user = ContextHolderUtils.getUserOrElseThrow();
        UserNotification notification = userNotificationFunction.apply(user, notificationId);
        notification.setRead(true);
        repository.save(user);
        UserNotificationResponse response = mapper.apply(notification);

        return new ApiResponse(true, "success", response);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        User user = ContextHolderUtils.getUserOrElseThrow();
        UserNotification notification = userNotificationFunction.apply(user, notificationId);
        user.getNotifications().remove(notification);
        repository.save(user);
    }
}