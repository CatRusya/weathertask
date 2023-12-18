package com.example.weathertask.web.controller;

import com.example.weathertask.AbstractTest;
import com.example.weathertask.data.TestDataBuilder;
import com.example.weathertask.domain.weather.Weather;
import com.example.weathertask.web.dto.weather.WeatherDto;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebTestClient
@RequiredArgsConstructor
class WeatherControllerTest extends AbstractTest {

    @Value("${security.jwt.header}")
    String tokenHeaderName;

    @Value("${security.jwt.token-prefix}")
    String tokenPrefix;

    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Autowired
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Sql(value = "/db.dml/schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/fill-schema-for-creating.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/drop-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCreateWeatherRecord() throws Exception {

        Weather createdWeather = Weather.builder()
                .date(LocalDate.parse("1985-01-01"))
                .lat(36.1189)
                .lon(-86.689)
                .city("Nashville")
                .state("Tennessee")
                .temperatures(List.of(17.3, 16.8, 16.4, 16.0, 15.6, 15.3))
                .build();

        mockMvc.perform(post("/weather")
                        .header(tokenHeaderName,
                                tokenPrefix + testDataBuilder.getValidTestAccessToken("johndoe@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdWeather)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Sql(value = "/db.dml/schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/fill-schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/drop-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void failsIfJwtInRequestHeaderIsExpiredReturnsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/api/v1/weather")
                        .header(tokenHeaderName, tokenPrefix
                                + testDataBuilder.getExpiredTestAccessToken("johndoe@gmail.com")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(value = "/db.dml/schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/fill-schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/drop-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetWeatherRecordById() throws Exception {
        Long weatherId = 5L;
        WeatherDto expectedWeather = testDataBuilder.createWeatherDto();

        String contentAsString = this.mockMvc.perform(get("/weather/{weather}", weatherId)
                        .header(tokenHeaderName, tokenPrefix
                                + testDataBuilder.getValidTestAccessToken("johndoe@gmail.com")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        WeatherDto actualWeather= objectMapper.readValue(contentAsString,
                new TypeReference<WeatherDto>() {});
        assertEquals(expectedWeather, actualWeather);
    }

}