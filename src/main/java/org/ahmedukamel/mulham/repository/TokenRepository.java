package org.ahmedukamel.mulham.repository;

import jakarta.transaction.Transactional;
import org.ahmedukamel.mulham.model.AccountToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<AccountToken, UUID> {
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE AccountToken t
            SET t.revoked = true
            WHERE t.user = :user
            AND t.type = :type
            """)
    void revokeUserTokens(@Param(value = "user") User user, @Param(value = "type") TokenType type);

    boolean existsByCode(int code);
}