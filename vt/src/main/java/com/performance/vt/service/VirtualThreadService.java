package com.performance.vt.service;

import com.performance.vt.dto.MovieDiscoverResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class VirtualThreadService {

    private final RestClient tmdbRestClient;

    public VirtualThreadService(RestClient tmdbRestClient) {
        this.tmdbRestClient = tmdbRestClient;
    }

    public MovieDiscoverResponse getPopularMovies(int page, String sortBy) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            var future = executor.submit(() ->
                    tmdbRestClient.get()
                            .uri(uriBuilder -> uriBuilder
                                    .path("/discover/movie")
                                    .queryParam("include_adult", false)
                                    .queryParam("include_video", false)
                                    .queryParam("language", "en-US")
                                    .queryParam("page", page)
                                    .queryParam("sort_by", sortBy)
                                    .build())
                            .retrieve()
                            .body(MovieDiscoverResponse.class)
            );

            return future.get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}