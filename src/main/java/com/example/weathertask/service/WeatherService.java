package com.example.weathertask.service;

import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.web.dto.weather.WeatherDto;

import java.time.LocalDate;
import java.util.List;

public interface WeatherService {

    Weather getRecordById(Long id);

    List<Weather> getRecords(LocalDate date, List<String> cities, String sort);

    Weather createRecord(WeatherDto weatherDto);



}
