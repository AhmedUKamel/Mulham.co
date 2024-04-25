package org.ahmedukamel.mulham.controller.notification;

import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.model.enumeration.NotificationType;
import org.ahmedukamel.mulham.service.notification.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/user/notification")
public class UserNotificationController {
    final IUserNotificationService service;

    public UserNotificationController(UserNotificationService service) {
        this.service = service;
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok().body(service.getNotifications());
    }

    @GetMapping(value = "unread")
    public ResponseEntity<?> getUnreadNotificationsCount() {
        return ResponseEntity.ok().body(service.getUnreadNotificationsCount());
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getNotifications(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getNotifications(pageSize, pageNumber));
    }

    @GetMapping(value = "type")
    public ResponseEntity<?> getNotifications(@RequestParam(value = "type") NotificationType notificationType) {
        return ResponseEntity.ok().body(service.getNotifications(notificationType));
    }

    @GetMapping(value = "type/page")
    public ResponseEntity<?> getNotifications(@RequestParam(value = "type") NotificationType notificationType,
                                              @Min(value = 1) @RequestParam(value = "size") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getNotifications(notificationType, pageSize, pageNumber));
    }

    @PutMapping(value = "read/{notificationId}")
    public ResponseEntity<?> readNotification(@Min(value = 1) @PathVariable(value = "notificationId") Long notificationId) {
        return ResponseEntity.ok().body(service.readNotification(notificationId));
    }

    @DeleteMapping(value = "delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@Min(value = 1) @PathVariable(value = "notificationId") Long notificationId) {
        service.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}