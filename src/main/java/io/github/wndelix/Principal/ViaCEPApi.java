package io.github.wndelix.Principal;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;

public class ViaCEPApi {
    private HttpClient getClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    private HttpRequest getRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
    }

    List<CEPdto> getResponseEndereco(String url) {
        HttpResponse<String> response;
        HttpClient client = getClient();
        HttpRequest request = getRequest(url);

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (HttpTimeoutException e) {
            System.out.println("Tempo esgotado ao consultar a API. Tente novamente.");
            return null;
        } catch (IOException e) {
            System.out.println("Erro de rede. Verifique sua conexão.");
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("A API respondeu com status " + response.statusCode() + ".");
            return null;
        }

        try {
            Type listType = new TypeToken<List<CEPdto>>() {}.getType();
            return new Gson().fromJson(response.body(), listType);
        } catch (JsonSyntaxException e) {
            System.out.println("Resposta inesperada da API.");
            return null;
        }
    }

    CEPdto getResponseCEP(String url) {
        HttpResponse<String> response;
        HttpClient client = getClient();
        HttpRequest request = getRequest(url);

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (HttpTimeoutException e) {
            System.out.println("Tempo esgotado ao consultar a API. Tente novamente.");
            return null;
        } catch (IOException e) {
            System.out.println("Erro de rede. Verifique sua conexão.");
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        if (response.statusCode() != 200) {
            System.out.println("A API respondeu com status " + response.statusCode() + ".");
            return null;
        }

        try {
            return new Gson().fromJson(response.body(), CEPdto.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Resposta inesperada da API.");
            return null;
        }
    }
}