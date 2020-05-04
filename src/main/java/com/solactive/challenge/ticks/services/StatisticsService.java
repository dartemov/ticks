package com.solactive.challenge.ticks.services;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;
import com.solactive.challenge.ticks.statistics.InstrumentStatistics;
import com.solactive.challenge.ticks.statistics.TickStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class StatisticsService {
    private final InstrumentStatistics instrumentStatistics;
    private final TickStatistics tickStatistics;

    public StatisticsService(@Value("${statistics.window}") int windowSize,
                             @Value("${statistics.accuracy}") int accuracy,
                             @Value("${statistics.scale}") int scale) {
        this.instrumentStatistics = new InstrumentStatistics(windowSize, accuracy, scale);
        this.tickStatistics = new TickStatistics(windowSize, accuracy, scale);
    }

    @Async
    public CompletableFuture<Boolean> addTick(long currentTimestamp, Tick tick) {
        return CompletableFuture.supplyAsync(() -> instrumentStatistics.addTick(currentTimestamp, tick)
                && tickStatistics.addTick(currentTimestamp, tick));
    }

    @Async
    public CompletableFuture<Statistics> getInstrumentStatistics(String instrument, long timestamp) {
        return CompletableFuture.supplyAsync(() -> instrumentStatistics.statistics(instrument, timestamp));
    }

    @Async
    public CompletableFuture<Statistics> getTickStatistics(long timestamp) {
        return CompletableFuture.supplyAsync(() -> tickStatistics.statistics(timestamp));
    }
}
