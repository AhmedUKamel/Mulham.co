package org.ahmedukamel.mulham.repository;

import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCaseAndProvider(String email, Provider provider);

    Optional<User> findByProviderIdAndProvider(String providerId, Provider provider);

    boolean existsByEmailIgnoreCaseAndProvider(String email, Provider provider);
}