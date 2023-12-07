package com.example.weathertask.repository.mappers;

import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.web.dto.weather.WeatherDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface WeatherMapper extends Mappable<Weather, WeatherDto>{

}
