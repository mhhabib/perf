package com.performance.vt.controller;

import com.performance.vt.dto.MovieDiscoverResponse;
import com.performance.vt.service.VirtualThreadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class VirtualThreadController {
    private final VirtualThreadService virtualThreadService;

    public VirtualThreadController(VirtualThreadService service) {
        this.virtualThreadService = service;
    }

    @GetMapping("vthread")
    public MovieDiscoverResponse getMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "popularity.desc") String sortBy) {
        return virtualThreadService.getPopularMovies(page, sortBy);
    }
}