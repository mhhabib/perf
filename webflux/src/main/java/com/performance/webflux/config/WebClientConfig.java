package com.performance.webflux.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${tmdb.api.token}")
    private String apiToken;

    @Bean
    public WebClient tmdbWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.themoviedb.org/3")
                .defaultHeader("accept", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .build();
    }
}