package com.solactive.challenge.ticks.controllers;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("")
    public CompletableFuture<Statistics> statistics() {
        return statisticsService.getTickStatistics(System.currentTimeMillis());
    }

    @GetMapping("{instrument}")
    public CompletableFuture<Statistics> statisticsForInstrument(@PathVariable String instrument) {
        return statisticsService.getInstrumentStatistics(instrument, System.currentTimeMillis());
    }
}
