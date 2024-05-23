package example.manageuser.Config.JWT;

import io.jsonwebtoken.*;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String SECRET_KEY = "secret";
    private long accessTokenExpirationMs = 1000 * 60 * 60 * 10; // 10 hours
    private long refreshTokenExpirationMs = 1000 * 60 * 60 * 24 * 30; // 30 days

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate access token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, accessTokenExpirationMs);
    }

    // Generate refresh token
    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, refreshTokenExpirationMs);
    }

    // Create token with specific expiration time
    private String createToken(Map<String, Object> claims, String subject, long expirationMs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Validate refresh token
    public Boolean validateRefreshToken(String token) {
        return !isTokenExpired(token);
    }

    // Generate JWT cookie
    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = generateToken(userDetails.getUsername());
        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(false) // Set this to true if using HTTPS
                .path("/")
                .maxAge(accessTokenExpirationMs / 1000) // Cookie expires in access token expiration time
                .build();
    }
}
