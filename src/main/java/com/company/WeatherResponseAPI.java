package com.company;

import com.company.iss.SpaceResponse;
import com.company.weather.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class WeatherResponseAPI {
	// TODO optimize functions
	public static WeatherResponse getWeatherResponse(String location) {
		WebClient client = WebClient
				.create("https://api.openweathermap.org/data/2.5/weather?q=" + location +
						"&appid=6af3c38b2822faf4017b9256e6973339&units=imperial");

		WeatherResponse weatherResponse = null;

		try {
			Mono<WeatherResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(WeatherResponse.class);

			weatherResponse = response.share().block();
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >= 400 && statusCode <500){
				System.out.println("Client Error: City not found");
			}
			else if (statusCode >= 500 && statusCode <600){
				System.out.println("Server Error");
			}
			System.out.println("\tMessage: " + we.getMessage());
		}
		catch (Exception e) {
			System.out.println("\tAn error occurred: " + e.getMessage());
		}

		return weatherResponse;
	}

	public static WeatherResponse getWeatherResponse(SpaceResponse issLocation) {
		WebClient client = WebClient
				.create("https://api.openweathermap.org/data/2.5/weather?" +
						"lat=" + issLocation.iss_position.latitude +
						"&lon=" + issLocation.iss_position.longitude +
						"&appid=6af3c38b2822faf4017b9256e6973339&units=imperial");

		WeatherResponse weatherResponse = null;

		try {
			Mono<WeatherResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(WeatherResponse.class);

			weatherResponse = response.share().block();
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >= 400 && statusCode <500){
				System.out.println("Client Error: City not found");
			}
			else if (statusCode >= 500 && statusCode <600){
				System.out.println("Server Error");
			}
			System.out.println("\tMessage: " + we.getMessage());
		}
		catch (Exception e) {
			System.out.println("\tAn error occurred: " + e.getMessage());
		}

		return weatherResponse;
	}
}
