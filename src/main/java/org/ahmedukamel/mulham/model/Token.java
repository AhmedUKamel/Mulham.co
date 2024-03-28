package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Data;
import org.ahmedukamel.mulham.model.enumeration.TokenType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tokens", uniqueConstraints =
@UniqueConstraint(name = "TOKEN_CODE_UNIQUE_CONSTRAINT", columnNames = "code"))
public class Token {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(updatable = false)
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private TokenType type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiration;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private boolean revoked;
}