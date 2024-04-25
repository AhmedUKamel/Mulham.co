package org.ahmedukamel.mulham.repository;

import org.ahmedukamel.mulham.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {
}