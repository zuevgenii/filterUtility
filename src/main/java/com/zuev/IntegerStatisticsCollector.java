package com.zuev;
import java.util.List;
class IntegerStatisticsCollector implements StatisticsCollector<Integer> {
    private final boolean full;

    public IntegerStatisticsCollector(boolean full) {
        this.full = full;
    }

    @Override
    public Statistics collect(List<Integer> data) {
        if (full) {
            return new FullNumberStatistics(data);
        } else {
            return new BriefStatistics(data.size());
        }
    }
}