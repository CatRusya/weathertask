package com.example.weathertask.service.impl;

import com.example.weathertask.domain.exception.ResourceNotFoundException;
import com.example.weathertask.domain.user.Role;
import com.example.weathertask.domain.user.User;
import com.example.weathertask.repository.UserRepository;
import com.example.weathertask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public User getById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  @Transactional
  public User update(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return user;
  }

  @Override
  @Transactional
  public User create(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("User already exists.");
    }
    if (!user.getPassword().equals(user.getPasswordConfirmation())) {
      throw new IllegalStateException(
          "Password and password confirmation do not match.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Set<Role> roles = Set.of(Role.ROLE_USER);
    user.setRoles(roles);
    userRepository.save(user);
    return user;
  }

  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
