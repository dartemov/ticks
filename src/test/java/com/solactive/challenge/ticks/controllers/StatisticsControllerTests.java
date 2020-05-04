package com.solactive.challenge.ticks.controllers;

import com.solactive.challenge.ticks.models.Statistics;
import com.solactive.challenge.ticks.services.StatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTests {
    private static final Statistics statisticsStub =
            new Statistics.Builder()
                    .setAvg(1)
                    .setCount(1)
                    .setMax(1)
                    .setMin(1)
                    .build();
    private static final Statistics instrumentStatisticsStub =
            new Statistics.Builder()
                    .setAvg(1.5)
                    .setCount(2)
                    .setMax(2)
                    .setMin(1)
                    .build();

    @MockBean
    private StatisticsService statisticsService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        Mockito.when(statisticsService.getTickStatistics(anyLong()))
                .thenReturn(CompletableFuture.completedFuture(statisticsStub));
        Mockito.when(statisticsService.getInstrumentStatistics(anyString(), anyLong()))
                .thenReturn(CompletableFuture.completedFuture(instrumentStatisticsStub));
    }

    @Test
    public void correctResultForEntireStatistics() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/statistics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc
                .perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avg", is(statisticsStub.getAvg())))
                .andExpect(jsonPath("$.max", is(statisticsStub.getMax())))
                .andExpect(jsonPath("$.min", is(statisticsStub.getMin())))
                .andExpect(jsonPath("$.count", is((int) statisticsStub.getCount())));
    }

    @Test
    public void correctResultForInstrumentStatistics() throws Exception {
        MvcResult result = mockMvc
                .perform(get("/statistics/IBN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc
                .perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.avg", is(instrumentStatisticsStub.getAvg())))
                .andExpect(jsonPath("$.max", is(instrumentStatisticsStub.getMax())))
                .andExpect(jsonPath("$.min", is(instrumentStatisticsStub.getMin())))
                .andExpect(jsonPath("$.count", is((int) instrumentStatisticsStub.getCount())));
    }
}
