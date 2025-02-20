package com.zuev;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String outputDirectory = "."; // Текущая папка по умолчанию
        boolean appendToFile = false;
        String filePrefix = "";
        boolean fullStatistics = false;
        boolean briefStatistics = false;
        List<String> inputFiles = new ArrayList<>();

        // Обработка аргументов командной строки
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
                if (i + 1 < args.length) {
                    outputDirectory = args[i + 1];
                    i++;
                } else {
                    System.err.println("Ошибка: необходимо указать путь после опции -o");
                    return; // Завершаем работу, так как это критическая ошибка
                }
            } else if (args[i].equals("-a")) {
                appendToFile = true;
            } else if (args[i].equals("-p")) {
                if (i + 1 < args.length) {
                    filePrefix = args[i + 1];
                    i++;
                } else {
                    System.err.println("Ошибка: необходимо указать префикс после опции -p");
                    return; // Завершаем работу, так как это критическая ошибка
                }
            } else if (args[i].equals("-f")) {
                fullStatistics = true;
            } else if (args[i].equals("-s")) {
                briefStatistics = true;
            } else {

                inputFiles.add(args[i]);
            }
        }

        if (fullStatistics && briefStatistics) {
            System.err.println("Ошибка: Нельзя использовать одновременно опции -f и -s");
            return; // Завершаем работу, так как это критическая ошибка
        }

        // Настройка логирования
        try {
            java.util.logging.FileHandler fh = new java.util.logging.FileHandler("FileContentFilter.log", true);
            java.util.logging.SimpleFormatter formatter = new java.util.logging.SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);

        } catch (IOException e) {
            System.err.println("Не удалось настроить логирование: " + e.getMessage());
            e.printStackTrace();
        }


        boolean displayStatistics = fullStatistics || briefStatistics;

        // Создание экземпляров классов
        DataReader dataReader = new DefaultDataReader();
        DataWriter dataWriter = new DefaultDataWriter();

        StatisticsCollector<Integer> integerStatisticsCollector = new IntegerStatisticsCollector(fullStatistics);
        StatisticsCollector<Double> doubleStatisticsCollector = new DoubleStatisticsCollector(fullStatistics);
        StatisticsCollector<String> stringStatisticsCollector = new StringStatisticsCollector(fullStatistics);


        FileContentFilter filter = new FileContentFilter(dataReader, dataWriter,
                integerStatisticsCollector,
                doubleStatisticsCollector,
                stringStatisticsCollector);

        // Обработка файлов
        try {
            FileContentFilter.StatisticsResult result = filter.processFiles(inputFiles, outputDirectory, appendToFile, filePrefix);

            if (displayStatistics) {
                System.out.println("Статистика по целым числам: " + result.integerStatistics.toString());
                System.out.println("Статистика по вещественным числам: " + result.doubleStatistics.toString());
                System.out.println("Статистика по строкам: " + result.stringStatistics.toString());
            }


        } catch (Exception e) {
            logger.log(Level.SEVERE, "Произошла общая ошибка: ", e);
            System.err.println("Критическая ошибка: Произошла общая ошибка в программе.  " +
                    "Дальнейшая работа невозможна. Подробнее в логе.");
        }
    }
}
