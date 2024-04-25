package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "coupons", uniqueConstraints =
@UniqueConstraint(name = "COUPON_CODE_UNIQUE_CONSTRAINT", columnNames = "code"))
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Category category;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;
}