package com.solactive.challenge.ticks.services;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.models.Tick;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(ConcurrentTestRunner.class)
public class StatisticsServiceTests {
    private final ConcurrentLinkedQueue<Tick> ticks = new ConcurrentLinkedQueue<>(Arrays.asList(
            new Tick.Builder()
                    .setInstrument("IBM.N")
                    .setPrice(1)
                    .setTimestamp(1478192204000L)
                    .build(),
            new Tick.Builder()
                    .setInstrument("IBM.R")
                    .setPrice(4)
                    .setTimestamp(1478192204001L)
                    .build(),
            new Tick.Builder()
                    .setInstrument("IBM.N")
                    .setPrice(2)
                    .setTimestamp(1478192204002L)
                    .build(),
            new Tick.Builder()
                    .setInstrument("IBM.N")
                    .setPrice(3)
                    .setTimestamp(1478192204002L)
                    .build()
    ));

    private final StatisticsService statisticsService =
            new StatisticsService(60_000, 1000, 2);

    @Test
    @ThreadCount(4)
    public void addFourTicksConcurrently() throws ExecutionException, InterruptedException {
        Boolean result = statisticsService.addTick(1478192204003L, ticks.poll()).get();
        assertTrue(result);
    }

    @After
    public void testStatistics() throws ExecutionException, InterruptedException {
        Statistics tickStatistics = statisticsService.getTickStatistics(1478192204009L).get();
        assertEquals(4, tickStatistics.getCount());
        assertEquals(4, tickStatistics.getMax());
        assertEquals(1, tickStatistics.getMin());
        assertEquals(2.5, tickStatistics.getAvg());
    }

    @After
    public void testIBMNStatistics() throws ExecutionException, InterruptedException {
        Statistics tickStatistics = statisticsService.getInstrumentStatistics("IBM.N", 1478192204009L).get();
        assertEquals(3, tickStatistics.getCount());
        assertEquals(3, tickStatistics.getMax());
        assertEquals(1, tickStatistics.getMin());
        assertEquals(2, tickStatistics.getAvg());
    }

    @After
    public void testIBMRStatistics() throws ExecutionException, InterruptedException {
        Statistics tickStatistics = statisticsService.getInstrumentStatistics("IBM.R", 1478192204009L).get();
        assertEquals(1, tickStatistics.getCount());
        assertEquals(4, tickStatistics.getMax());
        assertEquals(4, tickStatistics.getMin());
        assertEquals(4, tickStatistics.getAvg());
    }

}
