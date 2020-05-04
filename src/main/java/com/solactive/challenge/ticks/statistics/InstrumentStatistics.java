package com.solactive.challenge.ticks.statistics;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;

import java.util.concurrent.ConcurrentHashMap;

public class InstrumentStatistics {
    private final ConcurrentHashMap<String, TickStatistics> holder = new ConcurrentHashMap<>();
    private final int windowSize;
    private final int accuracy;
    private final int scale;

    public InstrumentStatistics(int windowSize, int accuracy, int scale) {
        this.windowSize = windowSize;
        this.accuracy = accuracy;
        this.scale = scale;
    }

    public boolean addTick(long currentTimestamp, Tick tick) {
        return holder.computeIfAbsent(tick.getInstrument(), (instr) -> new TickStatistics(windowSize, accuracy, scale))
                .addTick(currentTimestamp, tick);
    }

    public Statistics statistics(String instrument, long timestamp) {
        return holder.getOrDefault(instrument, new TickStatistics(windowSize, accuracy, scale))
                .statistics(timestamp);
    }
}
