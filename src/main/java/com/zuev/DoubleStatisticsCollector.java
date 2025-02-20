package com.zuev;

import java.util.List;

class DoubleStatisticsCollector implements StatisticsCollector<Double> {
    private final boolean full;

    public DoubleStatisticsCollector(boolean full) {
        this.full = full;
    }

    @Override
    public Statistics collect(List<Double> data) {
        if (full) {
            return new FullNumberStatistics(data);
        } else {
            return new BriefStatistics(data.size());
        }
    }
}