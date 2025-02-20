package com.zuev;

// Краткая статистика
class BriefStatistics extends Statistics {
    private int count;

    public BriefStatistics(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Количество элементов: " + count;
    }
}