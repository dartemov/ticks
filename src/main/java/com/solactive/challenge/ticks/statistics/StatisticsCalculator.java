package com.solactive.challenge.ticks.statistics;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;

import java.math.BigDecimal;

public class StatisticsCalculator {
    private final int scale;

    public StatisticsCalculator(int scale) {
        this.scale = scale;
    }

    public Statistics addTick(Statistics previous, Tick tick) {
        Statistics result;
        double price = tick.getPrice();
        if (previous == null) {
            result = new Statistics.Builder()
                    .setAvg(price)
                    .setCount(1L)
                    .setMax(price)
                    .setMin(price)
                    .build();
        } else {
            long count = previous.getCount() + 1;
            double max = Math.max(previous.getMax(), price);
            double min = Math.min(previous.getMin(), price);
            BigDecimal previousAvg = BigDecimal.valueOf(previous.getAvg());
            double avg = previousAvg.multiply(BigDecimal.valueOf(count - 1))
                    .add(BigDecimal.valueOf(price))
                    .divide(BigDecimal.valueOf(count), scale, BigDecimal.ROUND_HALF_DOWN)
                    .doubleValue();

            result = new Statistics.Builder()
                    .setMin(min)
                    .setMax(max)
                    .setCount(count)
                    .setAvg(avg)
                    .build();
        }
        return result;
    }
}
