package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_notifications")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Notification notification;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Column(name = "is_read", nullable = false, columnDefinition = "bit(1) default false")
    private boolean read;
}