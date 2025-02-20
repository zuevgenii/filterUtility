package com.zuev;

import java.util.List;

// Интерфейс для сбора статистики
interface StatisticsCollector<T> {
    Statistics collect(List<T> data);
}
