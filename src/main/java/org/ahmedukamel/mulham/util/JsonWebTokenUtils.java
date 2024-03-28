package org.ahmedukamel.mulham.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ahmedukamel.mulham.constant.JwtConstants;
import org.ahmedukamel.mulham.model.User;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.yaml.snakeyaml.util.EnumUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JsonWebTokenUtils {
    public static String getEmail(String jwt) {
        return extractClaim(jwt.strip(), Claims::getSubject);
    }

    public static Provider getProvider(String jwt) {
        Claims claims = extractClaims(jwt.strip());
        return EnumUtils.findEnumInsensitiveCase(Provider.class, claims.get(JwtConstants.CLAIM_PROVIDER) + "");
    }

    public static String generateToken(User user) {
        return generateToken(user, Map.of(JwtConstants.CLAIM_PROVIDER, user.getProvider()));
    }

    private static String generateToken(User user, Map<String, Object> extraClaims) {
        long issuedAt = System.currentTimeMillis();
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + JwtConstants.EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JwtConstants.SIGNING_KEY));
    }
}