package com.company;

import com.company.cryptocurrency.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class CoinResponseAPI {
    public static CoinResponse getCoinResponse(String asset) {
        WebClient client = WebClient.create("https://rest.coinapi.io/v1/assets/" + asset +
                "?apikey=46263578-4E82-42B4-8C73-D3F2D7623310");

        CoinResponse coinResponse = null;

        try {
            Mono<CoinResponse[]> response = client
                    .get()
                    .retrieve()
                    .bodyToMono(CoinResponse[].class);

            coinResponse = response.share().block()[0];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            coinResponse = null;
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

        return coinResponse;
    }
}
