package com.example.weathertask.service;


import com.example.weathertask.web.dto.auth.JwtRequest;
import com.example.weathertask.web.dto.auth.JwtResponse;

public interface AuthService {

  JwtResponse login(JwtRequest loginRequest);

  JwtResponse refresh(String refreshToken);

}
