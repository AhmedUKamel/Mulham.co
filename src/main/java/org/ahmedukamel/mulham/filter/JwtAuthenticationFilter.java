package org.ahmedukamel.mulham.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ahmedukamel.mulham.constant.JwtConstants;
import org.ahmedukamel.mulham.dto.response.ApiResponse;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.UserRepository;
import org.ahmedukamel.mulham.service.token.IAccessTokenService;
import org.ahmedukamel.mulham.service.token.JsonWebTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final UserRepository repository;
    final IAccessTokenService service;

    public JwtAuthenticationFilter(UserRepository repository, JsonWebTokenService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isValidHeader(authorizationHeader)) {
            try {
                final String token = extractJwt(authorizationHeader);
                User user = service.getUser(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);

            } catch (Exception exception) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getWriter(), new ApiResponse(false, exception.getMessage(), ""));
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