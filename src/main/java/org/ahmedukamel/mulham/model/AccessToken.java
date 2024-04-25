package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "access_tokens")
public class AccessToken {
    @Id
    private String token;

    @Column(nullable = false, updatable = false)
    private Date creation;

    @Column(nullable = false, updatable = false)
    private Date expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;
}