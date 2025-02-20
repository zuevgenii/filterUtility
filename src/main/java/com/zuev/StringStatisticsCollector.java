package com.zuev;
import java.util.List;
class StringStatisticsCollector implements StatisticsCollector<String> {
    private final boolean full;

    public StringStatisticsCollector(boolean full) {
        this.full = full;
    }

    @Override
    public Statistics collect(List<String> data) {
        if (full) {
            return new FullStringStatistics(data);
        } else {
            return new BriefStatistics(data.size());
        }
    }
}
