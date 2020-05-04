package com.solactive.challenge.ticks.models;

import java.util.Objects;

public final class Statistics {
    private final double avg;
    private final double max;
    private final double min;
    private final long count;

    Statistics(double avg, double max, double min, long count) {
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Double.compare(that.avg, avg) == 0 &&
                Double.compare(that.max, max) == 0 &&
                Double.compare(that.min, min) == 0 &&
                count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(avg, max, min, count);
    }

    public static class Builder {
        private double avg;
        private double max;
        private double min;
        private long count;

        public Builder setAvg(double avg) {
            this.avg = avg;
            return this;
        }

        public Builder setMax(double max) {
            this.max = max;
            return this;
        }

        public Builder setMin(double min) {
            this.min = min;
            return this;
        }

        public Builder setCount(long count) {
            this.count = count;
            return this;
        }

        public Statistics build() {
            return new Statistics(avg, max, min, count);
        }
    }
}
