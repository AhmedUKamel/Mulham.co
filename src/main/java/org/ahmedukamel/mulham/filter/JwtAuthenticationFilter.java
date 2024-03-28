package org.ahmedukamel.mulham.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ahmedukamel.mulham.constant.JwtConstants;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.db.DatabaseFetcher;
import org.ahmedukamel.mulham.util.JsonWebTokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final UserRepository repository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isValidHeader(authorizationHeader)) {
            try {
                final String jwt = extractJwt(authorizationHeader);
                final String email = JsonWebTokenUtils.getEmail(jwt);
                final Provider provider = JsonWebTokenUtils.getProvider(jwt);

                if (StringUtils.hasLength(email)) {
                    User user = DatabaseFetcher.get(repository::findByEmailIgnoreCaseAndProvider, email, provider, User.class);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception exception) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ApiResponse apiResponse = new ApiResponse(false, exception.getMessage(), "");
                new ObjectMapper().writeValue(response.getWriter(), apiResponse);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private String extractJwt(String header) {
        return header.substring(JwtConstants.PREFIX.length());
    }

    private boolean isValidHeader(String header) {
        return StringUtils.hasLength(header) && header.startsWith(JwtConstants.PREFIX);
    }
}