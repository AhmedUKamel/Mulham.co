package org.ahmedukamel.mulham.service.notification;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.NotificationDto;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.mapper.notification.NotificationDtoMapper;
import org.ahmedukamel.mulham.model.Notification;
import org.ahmedukamel.mulham.model.UserNotification;
import org.ahmedukamel.mulham.repository.NotificationRepository;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationManagementService implements INotificationManagementService {
    final ExecutorService executor = Executors.newFixedThreadPool(2);

    final NotificationRepository notificationRepository;
    final FirebaseMessagingService service;
    final UserRepository userRepository;
    final NotificationDtoMapper mapper;

    @Override
    public Object createNotification(Object object) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(object, notification);

        Set<UserNotification> userNotificationList = userRepository.findAll()
                .parallelStream()
                .map(user -> {
                    UserNotification userNotification = new UserNotification();
                    userNotification.setUser(user);
                    userNotification.setNotification(notification);
                    return userNotification;
                })
                .collect(Collectors.toSet());
        notification.setUsers(userNotificationList);

        Notification savedNotification = notificationRepository.save(notification);
        NotificationDto response = mapper.apply(savedNotification);

        CompletableFuture.runAsync(() -> service.send(savedNotification), executor);

        return new ApiResponse(true, "created", response);
    }

    @Override
    public Object updateNotification(Long id, Object object) {
        Notification notification = DatabaseFetcher.get(notificationRepository::findById, id, Notification.class);

        BeanUtils.copyProperties(object, notification);
        notification.setId(id);

        Notification savedNotification = notificationRepository.save(notification);
        NotificationDto response = mapper.apply(savedNotification);

        return new ApiResponse(true, "updated", response);
    }

    @Override
    public Object getNotification(Long id) {
        Notification notification = DatabaseFetcher.get(notificationRepository::findById, id, Notification.class);
        NotificationDto response = mapper.apply(notification);

        return new ApiResponse(true, "get", response);
    }

    @Override
    public Object getNotifications() {
        List<NotificationDto> response = notificationRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Notification::getId))
                .map(mapper)
                .toList();

        return new ApiResponse(true, "get", response);
    }

    @Override
    public Object getNotifications(long pageSize, long pageNumber) {
        List<NotificationDto> response = notificationRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Notification::getId))
                .skip(pageSize * (pageNumber - 1))
                .limit(pageSize)
                .map(mapper)
                .toList();

        return new ApiResponse(true, "get", response);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = DatabaseFetcher.get(notificationRepository::findById, id, Notification.class);
        notificationRepository.delete(notification);
    }
}