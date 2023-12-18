package com.example.weathertask.web.controller;

import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.repository.mappers.WeatherMapper;
import com.example.weathertask.service.WeatherService;
import com.example.weathertask.web.dto.validation.OnCreate;
import com.example.weathertask.web.dto.weather.WeatherDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/weather")
@Tag(name = "Weather Controller", description = "User API")
@RequiredArgsConstructor
@Validated
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherMapper weatherMapper;

    @PostMapping
    @Operation(summary = "Creates a new weather data record")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity<WeatherDto> createWeatherRecord(@Validated(OnCreate.class)@RequestBody WeatherDto weatherDto) {
        log.info("Weather controller, method: create weather record for city name = {} and date = {}",
                weatherDto.getCity(), weatherDto.getDate());
        Weather createdWeather = weatherService.createRecord(weatherDto);
        return new ResponseEntity<>(weatherMapper.toDto(createdWeather),HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Returns records")
    public ResponseEntity <List<WeatherDto>> getWeatherRecords(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) List<String> city,
            @RequestParam(required = false) String sort
    ){
        log.info("Weather controller, method: return weather record for city/ies name={} and date ={}",
                city, date);
        List<Weather> weatherRecords = weatherService.getRecords(date, city, sort);
        return new ResponseEntity<>(weatherMapper.toDto(weatherRecords), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a record with the given id")
    public ResponseEntity<WeatherDto> getWeatherRecordById(@PathVariable("id") Long id) {
        log.info("Weather controller, method: get weather information for id={}", id);
        return new ResponseEntity<>(weatherMapper.toDto(weatherService.getRecordById(id)),
                HttpStatus.OK);
    }
}
