package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "booking")
    @JoinColumn(nullable = false)
    private Payment payment;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JoinColumn(updatable = false)
    private UserCoupon coupon;

    @Embedded
    @Column(nullable = false, updatable = false)
    private Address address;

    @Column(nullable = false, updatable = false)
    private String details;

    @Column(nullable = false, updatable = false)
    private LocalDateTime start;

    @Column(nullable = false, updatable = false)
    private LocalDateTime end;
}