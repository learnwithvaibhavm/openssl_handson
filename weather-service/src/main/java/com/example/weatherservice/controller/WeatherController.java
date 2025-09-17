package com.example.weatherservice.controller;

import com.example.weatherservice.model.WeatherResponse;
import com.example.weatherservice.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/weather")
@Validated
@Api(value = "Weather Controller", description = "Operations for retrieving weather information")
public class WeatherController {
    
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    @ApiOperation(value = "Get weather information for a city", response = WeatherResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved weather data"),
            @ApiResponse(code = 404, message = "City not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<WeatherResponse> getWeather(
            @ApiParam(value = "City name", required = true)
            @RequestParam @NotBlank(message = "City name cannot be empty") String city) {
        logger.info("Received weather request for city: {}", city);
        WeatherResponse weather = weatherService.getWeatherForCity(city);
        logger.info("Successfully retrieved weather data for city: {}", city);
        return ResponseEntity.ok(weather);
    }
}
