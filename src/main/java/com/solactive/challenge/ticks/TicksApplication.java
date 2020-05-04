package com.solactive.challenge.ticks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TicksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicksApplication.class, args);
    }

}
