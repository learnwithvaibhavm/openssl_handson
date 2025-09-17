import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WeatherResponse {
  city: string;
  temperature: number;
  humidity: number;
}

export interface WeatherError {
  status: number;
  error: string;
  message: string;
  timestamp: number[];
}

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  private baseUrl = 'https://localhost:8443/weather';

  constructor(private http: HttpClient) {}

  getWeather(city: string): Observable<WeatherResponse> {
    console.log('Making API request to:', `${this.baseUrl}?city=${city}`);
    return this.http.get<WeatherResponse>(
      `${this.baseUrl}?city=${city}`,
      {
        headers: {
          'Accept': 'application/json'
        }
      }
    );
  }
}
