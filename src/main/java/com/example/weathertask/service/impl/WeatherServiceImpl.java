package com.example.weathertask.service.impl;

import com.example.weathertask.domain.exception.InvalidRequestException;
import com.example.weathertask.domain.exception.ResourceNotFoundException;
import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.repository.WeatherRepository;
import com.example.weathertask.service.WeatherService;
import com.example.weathertask.web.dto.weather.WeatherDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Weather getRecordById(Long id) {
        return weatherRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("Weather with id= {} not found", id);
                    return new ResourceNotFoundException("Weather not found");
                });
    }

    @Override
    public List<Weather> getRecords(LocalDate date, List<String> cities, String sort) {
        List<Weather> weatherRecords;
        if (sort != null && cities == null){
            throw new InvalidRequestException("Cities must be added");
        }

        if(sort == null && date== null && cities == null){
            throw new InvalidRequestException("You should add information");
        }

        if (date != null) {
            weatherRecords = weatherRepository.findByDate(date);
        } else if (!cities.isEmpty()) {
            weatherRecords = weatherRepository.findByCityIgnoreCaseIn(cities);
        } else {
            weatherRecords = weatherRepository.findAll();
        }

        if (sort != null && sort.equals("-date")) {
            weatherRecords.sort((w1, w2) -> {
                int dateComparison = w2.getDate().compareTo(w1.getDate());
                return (dateComparison == 0) ? Long.compare(w1.getId(), w2.getId()) : dateComparison;
            });
        } else {
            weatherRecords.sort((w1, w2) -> {
                int dateComparison = w1.getDate().compareTo(w2.getDate());
                return (dateComparison == 0) ? Long.compare(w1.getId(), w2.getId()) : dateComparison;
            });
        }
        return weatherRecords;
    }

    @Override
    public Weather createRecord(WeatherDto weatherDto) {

        Weather createdWeather = new Weather().builder()
                .date(weatherDto.getDate())
                .lon(weatherDto.getLon())
                .lat(weatherDto.getLat())
                .city(weatherDto.getCity())
                .state(weatherDto.getState())
                .temperatures(Arrays.asList(weatherDto.getTemperatures()))
                .build();
        return weatherRepository.save(createdWeather);
    }
}
