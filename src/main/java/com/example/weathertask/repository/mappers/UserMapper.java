package com.example.weathertask.repository.mappers;

import com.example.weathertask.domain.user.User;
import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.web.dto.user.UserDto;
import com.example.weathertask.web.dto.weather.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {


}
