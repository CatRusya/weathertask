package com.example.weathertask.web.security;


import com.example.weathertask.domain.exception.AccessDeniedException;
import com.example.weathertask.domain.user.Role;
import com.example.weathertask.domain.user.User;
import com.example.weathertask.service.UserService;
import com.example.weathertask.web.dto.auth.JwtResponse;
import com.example.weathertask.web.security.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;
  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String createAccessToken(Long userId,
      String username,
      Set<Role> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    claims.put("roles", resolveRoles(roles));
    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getAccess());
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(validity)
        .signWith(key)
        .compact();
  }

  private List<String> resolveRoles(Set<Role> roles) {
    return roles.stream()
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  public String createRefreshToken(Long userId, String username) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(validity)
        .signWith(key)
        .compact();
  }

  public JwtResponse refreshUserTokens(String refreshToken) {
    JwtResponse jwtResponse = new JwtResponse();
    if (!validateToken(refreshToken)) {
      throw new AccessDeniedException();
    }
    Long userId = Long.valueOf(getId(refreshToken));
    User user = userService.getById(userId);
    jwtResponse.setId(userId);
    jwtResponse.setUsername(user.getUsername());
    jwtResponse.setAccessToken(
        createAccessToken(userId, user.getUsername(), user.getRoles()));
    jwtResponse.setRefreshToken(
        createRefreshToken(userId, user.getUsername()));
    return jwtResponse;
  }

  public boolean validateToken(String token) {
    Jws<Claims> claims = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
    return !claims.getBody().getExpiration().before(new Date());
  }

  private String getId(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .get("id")
        .toString();
  }

  private String getUsername(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Authentication getAuthentication(String token) {
    String username = getUsername(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails,
        "",
        userDetails.getAuthorities());
  }
}
