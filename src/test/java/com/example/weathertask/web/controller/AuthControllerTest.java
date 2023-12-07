package com.example.weathertask.web.controller;

import com.example.weathertask.AbstractTest;
import com.example.weathertask.web.dto.auth.JwtRequest;
import com.example.weathertask.web.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebTestClient
@RequiredArgsConstructor
class AuthControllerTest extends AbstractTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @Sql(value = "/db.dml/schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/fill-schema.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db.dml/drop-table.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void successfulAuthenticateShouldReturnEmailAndWellFormedAccessAndRefreshTokens()
            throws Exception {
        JwtRequest jwtRequest = JwtRequest.builder()
                .username("johndoe@gmail.com")
                .password("12345")
                .build();

        String contentAsString = this.mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseJson = objectMapper.readTree(contentAsString);

        assertEquals("johndoe@gmail.com", responseJson.get("username").asText());
        assertTrue(jwtTokenProvider.validateToken(responseJson.get("accessToken").asText()));
        assertTrue(jwtTokenProvider.validateToken(responseJson.get("refreshToken").asText()));
    }
}
