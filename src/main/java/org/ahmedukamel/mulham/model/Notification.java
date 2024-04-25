package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.mulham.model.enumeration.NotificationType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate start;

    @Column(nullable = false)
    private LocalDate end;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16, columnDefinition = "varchar(16) default 'ANNOUNCEMENT'")
    private NotificationType type = NotificationType.ANNOUNCEMENT;

    @Column(nullable = false)
    private String englishText;

    @Column(nullable = false)
    private String arabicText;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotification> users = new HashSet<>();
}