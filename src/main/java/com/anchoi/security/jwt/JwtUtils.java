package com.anchoi.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.anchoi.entity.RoleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.anchoi.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${anchoi.app.jwtSecret}")
  private String jwtSecret;

  @Value("${anchoi.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${anchoi.app.jwtCookieName}")
  private String jwtCookie;


  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
    return cookie;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public String generateJwtToken(Authentication authentication, RoleUser roleUser) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    Map<String, Object> claims = new HashMap<>();
    claims.put("name",userPrincipal.getUsername());
    claims.put("roleObject", roleUser.getObjectList());
    claims.put("roleActor", roleUser.getRoleList());
    return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .addClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
      throw e;
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

}
