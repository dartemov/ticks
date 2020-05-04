package com.solactive.challenge.ticks.statistics;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsCalculatorTests {
    private final StatisticsCalculator calculator = new StatisticsCalculator(2);

    private final Tick firstTick = new Tick.Builder()
            .setInstrument("IBM.N")
            .setPrice(143.82)
            .setTimestamp(1478192204000L)
            .build();
    private final Tick secondTick = new Tick.Builder()
            .setInstrument("IBM.N")
            .setPrice(549)
            .setTimestamp(1478192204001L)
            .build();
    private final Tick thirdTick = new Tick.Builder()
            .setInstrument("IBM.N")
            .setPrice(1)
            .setTimestamp(1478192204002L)
            .build();

    @Test
    public void resultForNullPrevious() {
        Statistics result = calculator.addTick(null, firstTick);
        double price = firstTick.getPrice();

        assertEquals(1, result.getCount());
        assertEquals(price, result.getAvg());
        assertEquals(price, result.getMax());
        assertEquals(price, result.getMin());
    }

    @Test
    public void resultForTwoTicks() {
        Statistics result = calculator.addTick(null, firstTick);
        result = calculator.addTick(result, secondTick);

        assertEquals(2, result.getCount());
        assertEquals(346.41, result.getAvg());
        assertEquals(549, result.getMax());
        assertEquals(143.82, result.getMin());
    }

    @Test
    public void resultForThreeTicks() {

        Statistics result = calculator.addTick(null, firstTick);
        result = calculator.addTick(result, secondTick);
        result = calculator.addTick(result, thirdTick);

        assertEquals(3, result.getCount());
        assertEquals(231.27, result.getAvg());
        assertEquals(549, result.getMax());
        assertEquals(1, result.getMin());
    }
}
