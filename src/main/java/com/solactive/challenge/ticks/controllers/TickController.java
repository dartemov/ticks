package com.solactive.challenge.ticks.controllers;

import com.solactive.challenge.ticks.models.Tick;
import com.solactive.challenge.ticks.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class TickController {

    private final StatisticsService statisticsService;

    @Autowired
    public TickController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/ticks")
    public CompletableFuture<ResponseEntity<?>> add(@RequestBody Tick tick) {
        return statisticsService.addTick(System.currentTimeMillis(), tick)
                .thenApply(result -> result ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
