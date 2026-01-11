package com.performance.vt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Value("${tmdb.api.token}")
    private String apiToken;

    @Bean
    public RestClient tmdbRestClient(RestClient.Builder builder) {

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        return builder
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .baseUrl("https://api.themoviedb.org/3")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }
}