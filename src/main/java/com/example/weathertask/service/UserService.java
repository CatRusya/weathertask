package com.example.weathertask.service;


import com.example.weathertask.domain.user.User;

public interface UserService {

  User getById(Long id);

  User getByUsername(String username);

  User update(User user);

  User create(User user);

  void delete(Long id);

}
