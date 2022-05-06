package com.company.api;

import com.company.iss.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * The {@code SpaceResponseAPI} class contains methods that build and returns
 * {@code SpaceResponse} objects.
 */
public class SpaceResponseAPI {
    /**
     * This method returns a SpaceResponse. Using the OpenNotify: Current ISS Location
     * API http://open-notify.org/Open-Notify-API/, the method finds the ISS coordinates
     * and returns its information.
     */
    public SpaceResponse getIssResponse() {
        WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json");

        SpaceResponse spaceResponse = null;

        try {
            Mono<SpaceResponse> response = client
                    .get()
                    .retrieve()
                    .bodyToMono(SpaceResponse.class);

            spaceResponse = response.share().block();
        }
        catch (WebClientResponseException we) {
            int statusCode = we.getRawStatusCode();
            if (statusCode >= 400 && statusCode <500){
                System.out.println("Client Error");
            }
            else if (statusCode >= 500 && statusCode <600){
                System.out.println("Server Error");
            }
            System.out.println("\tMessage: " + we.getMessage());
        }
        catch (Exception e) {
            System.out.println("\tAn error occurred: " + e.getMessage());
        }

        return spaceResponse;
    }
}
