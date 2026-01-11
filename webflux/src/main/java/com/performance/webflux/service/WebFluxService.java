package com.performance.webflux.service;

import com.performance.webflux.dto.MovieDiscoverResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class WebFluxService {

    private final WebClient tmdbWebClient;

    public WebFluxService(WebClient tmdbWebClient) {
        this.tmdbWebClient = tmdbWebClient;
    }

    public Mono<MovieDiscoverResponse> getPopularMovies(int page, String sortBy) {
        log.debug("Calling TMDB Discover Movies API for page {} sorted by {}", page, sortBy);

        return tmdbWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/discover/movie")
                        .queryParam("include_adult", false)
                        .queryParam("include_video", false)
                        .queryParam("language", "en-US")
                        .queryParam("page", page)
                        .queryParam("sort_by", sortBy)
                        .build())
                .retrieve()
                .bodyToMono(MovieDiscoverResponse.class)
                .doOnSuccess(response -> log.debug("API Response: Found {} movies",
                        response != null ? response.getResults().size() : 0))
                .doOnError(error -> log.error("API Error: {}", error.getMessage()));
    }
}