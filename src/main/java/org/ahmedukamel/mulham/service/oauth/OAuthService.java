package org.ahmedukamel.mulham.service.oauth;

import org.ahmedukamel.mulham.model.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Optional;

public interface OAuthService {
    Optional<User> authenticate(OAuth2AuthenticationToken authentication);
}
