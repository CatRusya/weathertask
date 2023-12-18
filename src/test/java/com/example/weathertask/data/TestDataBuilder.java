package com.example.weathertask.data;


import com.example.weathertask.domain.user.Role;
import com.example.weathertask.domain.user.User;
import com.example.weathertask.service.UserService;
import com.example.weathertask.web.dto.weather.WeatherDto;
import com.example.weathertask.web.security.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TestDataBuilder {

    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Value("${security.jwt.refresh-token.expiration}")
    String refreshJwtExpiration;

    @Mock
    private final JwtProperties jwtProperties;
    @Mock
    private final UserService userService;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String getValidTestAccessToken(String username) {
        User user = userService.getByUsername(username);
        return createAccessToken(user.getId(), username, user.getRoles());
    }

    public String getExpiredTestAccessToken(String username) {
        User user = userService.getByUsername(username);
        return createToken(user.getId(), username, user.getRoles());
    }

    private String createToken(Long userId, String username, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("roles", resolveRoles(roles));
        Date now = new Date(System.currentTimeMillis() - Long.valueOf(refreshJwtExpiration));
        Date validity = new Date(now.getTime());
        return Jwts.builder().setClaims(claims).setExpiration(validity).signWith(Keys.hmacShaKeyFor(secretKey.getBytes())).compact();

    }

    private String createAccessToken(Long userId, String username, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("roles", resolveRoles(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().setClaims(claims).setExpiration(validity).signWith(Keys.hmacShaKeyFor(secretKey.getBytes())).compact();

    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public WeatherDto createWeatherDto() {
        return WeatherDto.builder()
                .id(5L)
                .date(LocalDate.of(2023,12,07))
                .lat(33.4484)
                .lon(-112.0740)
                .city("Phoenix")
                .state("AZ")
                .build();
    }
}
