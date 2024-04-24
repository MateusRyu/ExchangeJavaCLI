package br.dev.ryu.ExchangeJavaCLI.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class WebApiConnector {
    protected String baseUri;
    protected HttpClient.Version httpVersion = HttpClient.Version.HTTP_2;
    protected HttpClient.Redirect redirect = HttpClient.Redirect.NORMAL;

    protected void setBaseUrl(String baseUri) {
        this.baseUri = baseUri;
    }

    protected HttpClient buildHttpClient(Duration timeout) {
        return HttpClient.newBuilder()
                .version(this.httpVersion)
                .followRedirects(this.redirect)
                .connectTimeout(timeout)
                .build();
    }

    protected HttpRequest buildHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .build();
    }

    protected HttpResponse<String> requestUri(URI uri, Duration timeout) throws IOException, InterruptedException {
        try (HttpClient client = buildHttpClient(timeout)) {
            HttpRequest request = buildHttpRequest(uri);
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }
}

