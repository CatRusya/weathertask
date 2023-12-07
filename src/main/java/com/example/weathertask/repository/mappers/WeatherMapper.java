package com.example.weathertask.repository.mappers;

import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.web.dto.weather.WeatherDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface WeatherMapper extends Mappable<Weather, WeatherDto>{

}
