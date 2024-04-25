package org.ahmedukamel.mulham.service.auth;

public interface IAuthService {
    Object login(Object request);

    void logout(String accessToken, String deviceToken);

    Object register(Object request);
}