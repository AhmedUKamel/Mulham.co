package org.ahmedukamel.mulham.controller.notification;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.ahmedukamel.mulham.dto.NotificationDto;
import org.ahmedukamel.mulham.service.notification.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "api/v1/notification")
public class NotificationManagementController {
    final INotificationManagementService service;

    public NotificationManagementController(NotificationManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@Valid @RequestBody NotificationDto request) {
        return ResponseEntity.created(URI.create("api/v1/notification")).body(service.createNotification(request));
    }

    @PutMapping(value = "{notificationId}")
    public ResponseEntity<?> updateNotification(@Min(value = 1) @PathVariable(value = "notificationId") Long notificationId,
                                                @Valid @RequestBody NotificationDto request) {
        return ResponseEntity.ok().body(service.updateNotification(notificationId, request));
    }

    @GetMapping(value = "{notificationId}")
    public ResponseEntity<?> getNotification(@Min(value = 1) @PathVariable(value = "notificationId") Long notificationId) {
        return ResponseEntity.ok().body(service.getNotification(notificationId));
    }

    @GetMapping(value = "all")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok().body(service.getNotifications());
    }

    @GetMapping(value = "page")
    public ResponseEntity<?> getNotifications(@Min(value = 1) @RequestParam(value = "size") long pageSize,
                                              @Min(value = 1) @RequestParam(value = "number") long pageNumber) {
        return ResponseEntity.ok().body(service.getNotifications(pageSize, pageNumber));
    }

    @DeleteMapping(value = "{notificationId}")
    public ResponseEntity<?> deleteNotification(@Min(value = 1) @PathVariable(value = "notificationId") Long notificationId) {
        service.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}