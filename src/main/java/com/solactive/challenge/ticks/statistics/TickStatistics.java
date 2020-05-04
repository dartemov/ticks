package com.solactive.challenge.ticks.statistics;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;

import java.util.concurrent.ConcurrentHashMap;

public class TickStatistics {
    private final StatisticsCalculator calculator;
    private final ConcurrentHashMap<Long, Statistics> holder;
    private final int windowSize;
    private final int accuracy;

    public TickStatistics(int windowSize, int accuracy, int scale) {
        this.windowSize = windowSize;
        this.accuracy = accuracy;
        this.calculator = new StatisticsCalculator(scale);
        this.holder = new ConcurrentHashMap<>(windowSize / accuracy);
    }

    public boolean addTick(long currentTimestamp, Tick tick) {
        long timestamp = tick.getTimestamp() / accuracy;
        if (currentTimestamp / accuracy - timestamp > windowSize / accuracy) {
            return false;
        } else {
            holder.keySet().removeIf(t -> t < currentTimestamp / accuracy);
            for (int i = 0; i < windowSize / accuracy; i++) {
                holder.compute(timestamp + i, (key, prev) -> calculator.addTick(prev, tick));
            }
            return true;
        }
    }

    public Statistics statistics(Long timestamp) {
        return holder.getOrDefault(timestamp / accuracy, new Statistics.Builder().build());
    }
}
