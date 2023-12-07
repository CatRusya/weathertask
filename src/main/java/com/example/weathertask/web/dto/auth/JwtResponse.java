package com.example.weathertask.web.dto.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

  private Long id;
  private String username;
  private String accessToken;
  private String refreshToken;
}
