package com.company;

import com.company.iss.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class IssResponseAPI {
    public static IssResponse getIssResponse() {
        WebClient client = WebClient.create("http://api.open-notify.org/iss-now.json");

        IssResponse issResponse = null;

        try {
            Mono<IssResponse> response = client
                    .get()
                    .retrieve()
                    .bodyToMono(IssResponse.class);

            issResponse = response.share().block();
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

        return issResponse;
    }
}
