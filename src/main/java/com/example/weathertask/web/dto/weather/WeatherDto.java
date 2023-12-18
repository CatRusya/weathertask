package com.example.weathertask.web.dto.weather;

import com.example.weathertask.web.dto.validation.OnCreate;
import com.example.weathertask.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@Builder
@Schema(description = "Weather DTO")
public class WeatherDto {

    @Schema(description = "User 1", example = "1")
    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    private Double lat;

    @NotNull(message = "Must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    private Double lon;

    @Schema(description = "City name", example = "Nashville")
    @NotNull(message = "City name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "City name must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @Schema(description = "State name", example = "Tennessee")
    @NotNull(message = "State name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "City name must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String state;

    @NotNull(message = "Must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    private Double [] temperatures;
}

