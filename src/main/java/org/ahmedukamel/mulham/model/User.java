package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.mulham.model.enumeration.Gender;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.model.enumeration.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "USER_UNIQUE_EMAIL_CONSTRAINT", columnNames = {"email", "provider"}),
        @UniqueConstraint(name = "USER_UNIQUE_PHONE_CONSTRAINT", columnNames = {"countryCode", "nationalNumber"}),
        @UniqueConstraint(name = "USER_UNIQUE_PICTURE_CONSTRAINT", columnNames = "picture")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(updatable = false, length = 32)
    private String providerId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false, length = 16, columnDefinition = "varchar(16) default 'LOCAL'")
    private Provider provider = Provider.LOCAL;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16, columnDefinition = "varchar(16) default 'USER'")
    private Role role = Role.USER;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 16, columnDefinition = "varchar(16) default 'NOT_SELECTED'")
    private Gender gender = Gender.NOT_SELECTED;

    @Temporal(value = TemporalType.DATE)
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registration;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean enabled;

    @Column(name = "non_locked", nullable = false, columnDefinition = "bit(1) default true")
    private boolean accountNonLocked = true;

    @Column(nullable = false, length = 64)
    private String email;

    private String password;

    @Column(nullable = false, length = 32)
    private String firstName;

    @Column(nullable = false, length = 32)
    private String lastName;

    @Column(length = 64)
    private String picture;

    private Integer countryCode;

    private Long nationalNumber;

    @ElementCollection
    @CollectionTable(name = "device_tokens", joinColumns = @JoinColumn(nullable = false))
    @Column(name = "device_token", nullable = false)
    private Set<String> deviceTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountToken> accountTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccessToken> accessTokens = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserNotification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setEmail(String email) {
        this.email = email.strip().toLowerCase();
    }
}