package main.chap09;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CardNumberValidator {

    private String server;
    public CardNumberValidator(String server){
        this.server= server;
    }

    public String validate(String cardNumber){
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(server +"/card"))
                .header("Content-Type","text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(cardNumber))
                .timeout(Duration.ofSeconds(3))
                .build();

        try{
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return switch (response.body()) {
                case "ok" -> "VALID";
                case "bad" -> "INVALID";
                case "expired" -> "EXPIRED";
                case "theft" -> "THEFT";
                default -> "UNKNOWN";
            };
        } catch (IOException  | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
