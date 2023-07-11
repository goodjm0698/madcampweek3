package madcamp.second.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenUtil {
    @Value("${app.jwtSecret}")
    String jwtSecret;

    @Value("${app.jwtExpirationsMs}")
    private int jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init()
    {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(jwtSecret);
        this.key = new SecretKeySpec(decodedKey, "HmacSHA512");
    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        String principal = Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Authentication or Principal is null"));

        return Jwts.builder()
                .setSubject(principal)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }

    public Long extractUserId(String token)
    {
        String subject = Optional.ofNullable(token)
                .map(t -> Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(t).getBody().getSubject())
                .orElseThrow(() -> new IllegalArgumentException("Token is null"));

        return Long.parseLong(subject);
    }
}
