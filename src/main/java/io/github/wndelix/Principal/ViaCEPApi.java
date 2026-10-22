package io.github.wndelix.Principal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

// TODO: adicionar retorno null para respostas da api sem resultado. Provavelmente será implementado se aproveitando de erro 404.

public class ViaCEPApi {
    private String url = "https://viacep.com.br/ws/";

    public String buildUrl(String busca) {
        return url + busca + "/json";
    }

    private HttpClient getClient() {
        return HttpClient.newHttpClient();
    }

    private HttpRequest getRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET().build();
    }

    List<CEPdto> getResponseEndereco(String url) {
        HttpResponse<String> response = null;
        var client = getClient();
        var request = getRequest(url);
        Gson gson = new Gson();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        String jsonBody = response.body();
        Type listType = new TypeToken<List<CEPdto>>() {}.getType();

        List<CEPdto> enderecos = gson.fromJson(jsonBody, listType);
        return enderecos;
    }

    CEPdto getResponseCEP(String url) {
        HttpResponse<String> response = null;
        var client = getClient();
        var request = getRequest(url);
        Gson gson = new Gson();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String jsonBody = response.body();
        return gson.fromJson(jsonBody, CEPdto.class);
    }
}
