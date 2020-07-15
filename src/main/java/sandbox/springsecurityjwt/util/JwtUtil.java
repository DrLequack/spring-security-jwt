package sandbox.springsecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

  private String SECRET_KEY = "paine asdasdasdasdasdasidhgauisdyfgkuasdgfkjhasdgfjkhsadgfjasdhfkgk";

  public String extractUserName(String token) {
    return extractClaims(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaims(token, Claims::getExpiration);
  }

  public <T> T extractClaims(String token, Function<Claims, T> claimsFunction) {
    final Claims claims = extractAllClaims(token);
    return claimsFunction.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("scope",userDetails.getAuthorities());
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

}
