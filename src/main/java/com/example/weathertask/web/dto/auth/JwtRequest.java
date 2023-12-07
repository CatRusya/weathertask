package com.example.weathertask.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for login")
public class JwtRequest {

  @Schema(description = "email", example = "johndoe@gmail.com")
  @NotNull(message = "Username must be not null")
  private String username;

  @Schema(description = "email", example = "12345")
  @NotNull(message = "Password must be not null")
  private String password;
}
