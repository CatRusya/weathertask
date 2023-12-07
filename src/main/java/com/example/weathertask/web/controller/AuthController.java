package com.example.weathertask.web.controller;

import com.example.weathertask.service.AuthService;
import com.example.weathertask.web.dto.auth.JwtRequest;
import com.example.weathertask.web.dto.auth.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  @Operation(summary = "Login method")
  public ResponseEntity <JwtResponse> login(@Validated @RequestBody JwtRequest loginRequest) {
    log.info("Log in request in Auth Controller. User email: {}", loginRequest.getUsername());
    return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
  }

  @PostMapping("/refresh")
  @Operation(summary = "Method for getting new access-token.")
  public ResponseEntity <JwtResponse> refresh(@RequestBody String refreshToken) {
    log.info("Token refresh 'request' in auth-controller");
    return new ResponseEntity<>(authService.refresh(refreshToken), HttpStatus.OK);
  }
}
