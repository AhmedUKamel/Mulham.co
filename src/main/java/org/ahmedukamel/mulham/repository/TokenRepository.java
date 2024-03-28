package org.ahmedukamel.mulham.repository;

import org.ahmedukamel.mulham.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
}
