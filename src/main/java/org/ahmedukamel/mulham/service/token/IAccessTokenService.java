package org.ahmedukamel.mulham.service.token;

import org.ahmedukamel.mulham.model.User;

public interface IAccessTokenService {
    String generateToken(User user);

    User getUser(String token);

    void revokeToken(String token);

    void revokeTokens(User user);
}