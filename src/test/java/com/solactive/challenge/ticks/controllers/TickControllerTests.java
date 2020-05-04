package com.solactive.challenge.ticks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solactive.challenge.ticks.models.Tick;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TickControllerTests {
    private static final Tick pastTick = new Tick.Builder()
            .setInstrument("past")
            .setPrice(1d)
            .setTimestamp(1000L)
            .build();
    private static final Tick regularTick = new Tick.Builder()
            .setInstrument("regular")
            .setPrice(1d)
            .setTimestamp(1001L)
            .build();

    @MockBean
    private StatisticsService statisticsService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        Mockito.when(statisticsService.addTick(anyLong(), eq(regularTick)))
                .thenReturn(CompletableFuture.completedFuture(true));
        Mockito.when(statisticsService.addTick(anyLong(), eq(pastTick)))
                .thenReturn(CompletableFuture.completedFuture(false));
    }

    @Test
    public void regularTicksReturnCreated() throws Exception {
        String regularTickJson = new ObjectMapper().writeValueAsString(regularTick);
        MvcResult result = mockMvc
                .perform(post("/ticks")
                        .content(regularTickJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc
                .perform(asyncDispatch(result))
                .andExpect(status().isCreated());
    }

    @Test
    public void pastTickReturnNoContent() throws Exception {
        String regularTickJson = new ObjectMapper().writeValueAsString(pastTick);
        MvcResult result = mockMvc
                .perform(post("/ticks")
                        .content(regularTickJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        mockMvc
                .perform(asyncDispatch(result))
                .andExpect(status().isNoContent());
    }
}
