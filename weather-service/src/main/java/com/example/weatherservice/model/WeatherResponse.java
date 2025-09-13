package com.example.weatherservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Weather information response")
public class WeatherResponse {
    @ApiModelProperty(notes = "City name")
    private String city;
    
    @ApiModelProperty(notes = "Current temperature in Celsius")
    private double temperature;
    
    @ApiModelProperty(notes = "Weather description")
    private String description;
    
    @ApiModelProperty(notes = "Humidity percentage")
    private int humidity;
}
