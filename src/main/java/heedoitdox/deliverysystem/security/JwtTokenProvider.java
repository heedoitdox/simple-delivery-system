package heedoitdox.deliverysystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long expirationTime = 1000 * 60 * 60 * 24;

    public JwtTokenProvider(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public JwtTokenProvider() {}

    public String create(String identifier) {
        Claims claims = Jwts.claims().setSubject(identifier);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact();
    }

    public Boolean isValid(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        return getClaimsJws(token)
            .getBody()
            .getSubject();
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey.getEncoded())
            .build()
            .parseClaimsJws(token);
    }

}
