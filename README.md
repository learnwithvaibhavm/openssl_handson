# Weather Service

A Spring Boot microservice that provides real-time weather information by integrating with the OpenWeatherMap API. This educational project demonstrates best practices in building RESTful services with Spring Boot.
This microservice is secured with HTTPS (TLS 1.3) using OpenSSL-generated certificates and a custom Root CA for local development and testing.

## Features

- RESTful endpoint to fetch current weather data by city name
- Integration with OpenWeatherMap API
- Comprehensive logging system with file rotation
- Swagger API documentation
- SSL/TLS support
- Clean architecture with proper separation of concerns

## Technology Stack

- Java 11
- Spring Boot 2.7.0
- Spring MVC
- Springfox Swagger 3.0.0
- Logback for logging
- Maven for dependency management

## Prerequisites

- JDK 11 or later
- Maven 3.6 or later
- OpenWeatherMap API key (get it from [OpenWeatherMap](https://openweathermap.org/api))

## Getting Started

### Configuration

1. Clone the repository:
```bash
git clone <your-repository-url>
cd weather-service
```

2. Configure application properties in `src/main/resources/application.yml`:
```yaml
weather:
  api:
    key: your-api-key-here
```

3. SSL Configuration (optional):
The service comes with SSL enabled by default. SSL properties can be configured in `application.yml`:
```yaml
server:
  port: 8443
  ssl:
    enabled: true
    key-store: classpath:server.p12
    key-store-password: changeit
    key-store-type: PKCS12
    key-alias: mycert
```

### Building and Running

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The service will start on https://localhost:8443

## API Documentation

### Swagger UI
Access the Swagger UI at: https://localhost:8443/swagger-ui/

### API Endpoint

- **GET /weather**
  - Query Parameter: `city` (required)
  - Returns: Current weather information including temperature, humidity, and description
  - Example: `GET /weather?city=London`

### Sample Response

```json
{
    "city": "London",
    "temperature": 18.5,
    "description": "scattered clouds",
    "humidity": 75
}
```

## Testing

### Using cURL
```bash
curl -k "https://localhost:8443/weather?city=London"
```
Note: The `-k` flag is used to skip SSL certificate verification

### Using Postman
1. Create a new GET request
2. URL: `https://localhost:8443/weather?city=London`
3. If SSL is enabled, disable SSL certificate verification in Settings

## Logging

- Console logging with colored output
- File-based logging with rotation
- Log files location: `./logs/weather-service.log`
- Archived logs: `./logs/archived/`
- Debug level logging for the application package
- Info level for other components

## Project Structure

```
weather-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/weatherservice/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       └── model/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── logback-spring.xml
└── pom.xml
```

## Error Handling

The service includes comprehensive error handling for:
- Invalid city names
- API communication errors
- Server-side errors

## Contributing

Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

MIT License

## Acknowledgments

- OpenWeatherMap for providing the weather data API
- Spring Boot team for the excellent framework
- Springfox team for Swagger integration
