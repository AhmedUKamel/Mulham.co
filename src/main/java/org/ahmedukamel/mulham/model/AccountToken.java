package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.mulham.model.enumeration.TokenType;
import org.ahmedukamel.mulham.service.mail.Email;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account_tokens", uniqueConstraints =
@UniqueConstraint(name = "TOKEN_CODE_UNIQUE_CONSTRAINT", columnNames = "code"))
public class AccountToken implements Email {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private int code;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private TokenType type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean used;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Override
    public String getReceiver() {
        return this.user.getEmail();
    }

    @Override
    public Map<String, ?> getVariables() {
        return Map.of(
                "name", this.user.getFirstName(),
                "link", "%s?token=%s&email=%s".formatted("https://api.mulham.co/p/activate", this.getId(), this.getUser().getEmail()),
                "code", this.code,
                "expiration", this.expiration.format(DateTimeFormatter.ofPattern("E, d MMM yyyy HH:mm"))
        );
    }
}