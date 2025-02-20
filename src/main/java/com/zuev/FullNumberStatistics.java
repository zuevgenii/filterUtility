package com.zuev;

import java.util.List;

// Полная статистика для чисел
class FullNumberStatistics extends Statistics {
    private int count;
    private double min;
    private double max;
    private double sum;
    private double average;

    public FullNumberStatistics(List<? extends Number> data) {
        if (data.isEmpty()) {
            this.count = 0;
            this.min = 0;
            this.max = 0;
            this.sum = 0;
            this.average = 0;
            return;
        }

        this.count = data.size();
        this.min = data.get(0).doubleValue();
        this.max = data.get(0).doubleValue();
        this.sum = 0;

        for (Number number : data) {
            double value = number.doubleValue();
            this.sum += value;
            if (value < this.min) {
                this.min = value;
            }
            if (value > this.max) {
                this.max = value;
            }
        }

        this.average = this.sum / this.count;
    }

    @Override
    public String toString() {
        return "Количество элементов: " + count +
                ", Min: " + min +
                ", Max: " + max +
                ", Sum: " + sum +
                ", Average: " + average;
    }
}
