package com.example.weathertask.repository;

import com.example.weathertask.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findAll();
    List<Weather> findByDate(LocalDate date);
    List<Weather> findByCityIgnoreCaseIn(List<String> cities);


}
