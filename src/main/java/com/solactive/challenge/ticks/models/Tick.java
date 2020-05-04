package com.solactive.challenge.ticks.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = Tick.Builder.class)
public final class Tick {
    private final String instrument;
    private final double price;
    private final long timestamp;

    private Tick(String instrument, double price, long timestamp) {
        this.instrument = instrument;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getInstrument() {
        return instrument;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tick tick = (Tick) o;
        return Double.compare(tick.price, price) == 0 &&
                timestamp == tick.timestamp &&
                instrument.equals(tick.instrument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument, price, timestamp);
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String instrument;
        private double price;
        private long timestamp;

        public Builder setInstrument(String instrument) {
            this.instrument = instrument;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Tick build() {
            return new Tick(instrument, price, timestamp);
        }
    }
}
