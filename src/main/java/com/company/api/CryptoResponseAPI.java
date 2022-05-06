package com.company.api;

import com.company.cryptocurrency.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * The {@code CryptoResponseAPI} class contains methods that build and returns
 * {@code CryptoResponse} objects.
 */
public class CryptoResponseAPI {
    /**
     * Returns a CryptoResponse object based on the String that is passed into
     * the asset parameter. Using the CoinAPI from https://www.coinapi.io/, this method finds a
     * cryptocurrency and returns its information.
     *
     * This JSON response is a list of CryptoResponses, therefore the [] in the Mono type.
     * We grab response.share().block()[0] because if we get a JSON response, it
     * will be the first item in the list.
     *
     * @param  asset
     *         String that is the crypto asset abbreviated.
     */
    public CryptoResponse getCoinResponse(String asset) {
        WebClient client = WebClient.create("https://rest.coinapi.io/v1/assets/" + asset +
                "?apikey=46263578-4E82-42B4-8C73-D3F2D7623310");

        CryptoResponse cryptoResponse = null;

        try {
            Mono<CryptoResponse[]> response = client
                    .get()
                    .retrieve()
                    .bodyToMono(CryptoResponse[].class);

            cryptoResponse = response.share().block()[0];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            cryptoResponse = null;
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

        return cryptoResponse;
    }
}
