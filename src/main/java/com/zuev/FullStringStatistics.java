package com.zuev;
import java.util.List;
// Полная статистика для строк
class FullStringStatistics extends Statistics {
    private int count;
    private int minLength;
    private int maxLength;

    public FullStringStatistics(List<String> data) {
        if (data.isEmpty()) {
            this.count = 0;
            this.minLength = 0;
            this.maxLength = 0;
            return;
        }

        this.count = data.size();
        this.minLength = data.get(0).length();
        this.maxLength = data.get(0).length();

        for (String str : data) {
            int length = str.length();
            if (length < this.minLength) {
                this.minLength = length;
            }
            if (length > this.maxLength) {
                this.maxLength = length;
            }
        }
    }
    @Override
    public String toString() {
        return "Количество элементов: " + count +
                ", Min Length: " + minLength +
                ", Max Length: " + maxLength;
    }
}