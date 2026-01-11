package com.performance.webflux.controller;

import com.performance.webflux.dto.MovieDiscoverResponse;
import com.performance.webflux.service.WebFluxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/movies")
public class WebFluxController {
    private final WebFluxService webfluxService;

    public WebFluxController(WebFluxService service){
        this.webfluxService = service;
    }

    @GetMapping("flux")
    public Mono<MovieDiscoverResponse> getMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "popularity.desc") String sortBy) {
        return webfluxService.getPopularMovies(page, sortBy);
    }
}
