import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WeatherService, WeatherResponse } from './weather.service';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
  imports: [CommonModule, FormsModule]
})
export class AppComponent {
  city: string = '';
  weatherData: WeatherResponse[] = [];
  loading: boolean = false;
  error: string | null = null;

  constructor(private weatherService: WeatherService) {
    // Debug: Log when component is instantiated
    console.log('AppComponent initialized');
  }

  // Add method to clear data
  clearWeatherData() {
    this.weatherData = [];
  }

  fetchWeather() {
    if (!this.city) {
      this.error = 'Please enter a city name';
      return;
    }

    this.loading = true;
    this.error = null;
    console.log('Fetching weather for city:', this.city);

    this.weatherService.getWeather(this.city).subscribe({
      next: (data) => {
        console.log('Received weather data:', data);
        this.weatherData.push(data);
        this.city = '';
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching weather:', err);
        if (err.error && err.error.message) {
          this.error = `${err.error.error}: ${err.error.message}`;
        } else {
          this.error = 'Failed to fetch weather data. Please try again.';
        }
        this.loading = false;
      }
    });
  }
}
