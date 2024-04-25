package org.ahmedukamel.mulham.service.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.ahmedukamel.mulham.constant.JwtConstants;
import org.ahmedukamel.mulham.model.AccessToken;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Transactional
public class JsonWebTokenService implements IAccessTokenService {
    @Value(value = "${application.security.access-token.secret-key}")
    private String secretKey;

    @Value(value = "${application.security.access-token.expiration}")
    private long expiration;

    final AccessTokenRepository repository;

    public JsonWebTokenService(AccessTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getUser(final String token) {
        AccessToken accessToken = getAccessToken(token);

        User user = accessToken.getUser();
        if (!user.isEnabled()) throw new DisabledException("User account is disabled.");
        if (!user.isAccountNonLocked()) throw new LockedException("User account is locked.");

        return user;
    }

    @Override
    public void revokeToken(String token) {
        AccessToken accessToken = getAccessToken(token);
        accessToken.setRevoked(true);
        repository.save(accessToken);
    }

    @Override
    public void revokeTokens(User user) {
        Consumer<AccessToken> revoke = accessToken -> accessToken.setRevoked(true);
        user.getAccessTokens()
                .stream()
                .peek(revoke)
                .forEach(repository::save);
    }

    private AccessToken getAccessToken(String token) {
        if (isExpired(token)) throw new JwtException("Token %s is expired.".formatted(token));

        AccessToken accessToken = repository.findById(token)
                .orElseThrow(() -> new EntityNotFoundException("Token %s not found.".formatted(token)));

        if (accessToken.isRevoked()) throw new JwtException("Token %s is revoked.".formatted(token));
        return accessToken;
    }

    public boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(JwtConstants.CLAIM_ROLE, user.getRole());
        extraClaims.put(JwtConstants.CLAIM_PROVIDER, user.getProvider());
        return generateToken(user, extraClaims);
    }

    public String generateToken(User user, Map<String, Object> extraClaims) {
        long currentTimeMillis = System.currentTimeMillis();

        Date issuedAt = new Date(currentTimeMillis);
        Date expireAt = new Date(currentTimeMillis + expiration);

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setIssuer(JwtConstants.ISSUER)
                .setSubject(user.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expireAt)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setCreation(issuedAt);
        accessToken.setExpiration(expireAt);
        accessToken.setUser(user);

        repository.save(accessToken);
        return token;
    }

    private Claims getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey.strip()));
    }
}