package com.example.weatherservice.service;

import com.example.weatherservice.exception.InvalidCityException;
import com.example.weatherservice.exception.WeatherApiException;
import com.example.weatherservice.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.key}")
    private String apiKey;
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}&units=metric";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeatherForCity(String city) {
        logger.debug("Making API request to OpenWeatherMap for city: {}", city);
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(
                    WEATHER_API_URL,
                    Map.class,
                    city,
                    apiKey
            );

            if (response == null) {
                logger.error("Received null response from OpenWeatherMap API for city: {}", city);
                throw new WeatherApiException("Failed to get weather data for city: " + city);
            }
            
            if (response.containsKey("cod") && "404".equals(response.get("cod"))) {
                String errorMessage = (String) response.get("message");
                logger.error("City not found: {}", errorMessage);
                throw new InvalidCityException(errorMessage);
            }

            logger.debug("Successfully received response from OpenWeatherMap for city: {}", city);
            WeatherResponse weatherResponse = new WeatherResponse();
            weatherResponse.setCity(city);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> main = (Map<String, Object>) response.get("main");
            Double temperature = ((Number) main.get("temp")).doubleValue();
            Integer humidity = ((Number) main.get("humidity")).intValue();
            weatherResponse.setTemperature(temperature);
            weatherResponse.setHumidity(humidity);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            Map<String, Object> weather = weatherList.get(0);
            String description = (String) weather.get("description");
            weatherResponse.setDescription(description);

            logger.info("Weather data parsed successfully for city: {}. Temp: {}°C, Humidity: {}%, Desc: {}", 
                    city, temperature, humidity, description);
            return weatherResponse;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error fetching weather data for city: {}. Status: {}, Error: {}", 
                    city, e.getStatusCode(), e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new InvalidCityException("City not found: " + city);
            }
            throw new WeatherApiException("Error accessing weather API: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error fetching weather data for city: {}. Error: {}", city, e.getMessage());
            throw new WeatherApiException("Error fetching weather data: " + e.getMessage());
        }
    }
}
