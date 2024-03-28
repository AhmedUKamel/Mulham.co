package org.ahmedukamel.mulham.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.service.oauth.OAuthService;
import org.ahmedukamel.mulham.util.JsonWebTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final OAuthService oAuthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            Optional<User> optionalUser = oAuthService.authenticate(oAuth2AuthenticationToken);
            if (optionalUser.isPresent()) {
                String token = JsonWebTokenUtils.generateToken(optionalUser.get());
                new ObjectMapper().writeValue(response.getWriter(), new ApiResponse(true, "", Map.of("token", token)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}