package com.example.weatherservice.exception;

public class InvalidCityException extends RuntimeException {
    public InvalidCityException(String message) {
        super(message);
    }

    public InvalidCityException(String message, Throwable cause) {
        super(message, cause);
    }
}