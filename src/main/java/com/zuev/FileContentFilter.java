package com.zuev;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


class FileContentFilter {

    private static final Logger logger = Logger.getLogger(FileContentFilter.class.getName());

    private final DataReader dataReader;
    private final DataWriter dataWriter;
    private final StatisticsCollector<Integer> integerStatisticsCollector;
    private final StatisticsCollector<Double> doubleStatisticsCollector;
    private final StatisticsCollector<String> stringStatisticsCollector;

    public FileContentFilter(DataReader dataReader, DataWriter dataWriter,
                             StatisticsCollector<Integer> integerStatisticsCollector,
                             StatisticsCollector<Double> doubleStatisticsCollector,
                             StatisticsCollector<String> stringStatisticsCollector) {
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
        this.integerStatisticsCollector = integerStatisticsCollector;
        this.doubleStatisticsCollector = doubleStatisticsCollector;
        this.stringStatisticsCollector = stringStatisticsCollector;
    }


    public StatisticsResult processFiles(List<String> inputFiles, String outputDirectory, boolean appendToFile, String filePrefix) {
        List<Integer> integers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String inputFile : inputFiles) {
            try {
                List<String> lines = dataReader.readLines(inputFile);
                for (String line : lines) {
                    try {
                        integers.add(Integer.parseInt(line));
                    } catch (NumberFormatException e1) {
                        try {
                            doubles.add(Double.parseDouble(line));
                        } catch (NumberFormatException e2) {
                            strings.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Ошибка при обработке файла: " + inputFile + ". Файл будет пропущен.", e);
                System.err.println("Предупреждение: Ошибка при обработке файла " + inputFile +
                        ". Файл будет пропущен."); // Сообщаем пользователю
            }
        }

        // Запись данных в выходные файлы
        File integersFile = new File(outputDirectory, filePrefix + "integers.txt");
        try {
            if (!integers.isEmpty()) {
                dataWriter.writeData(integers, integersFile, appendToFile);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при записи целых чисел в файл: " + integersFile.getAbsolutePath(), e);
            System.err.println("Критическая ошибка: Не удалось записать целые числа в файл " + integersFile.getName() +
                    ". Дальнейшая работа может быть нестабильной.");
        }

        File doublesFile = new File(outputDirectory, filePrefix + "doubles.txt");
        try {
            if (!doubles.isEmpty()) {
                dataWriter.writeData(doubles, doublesFile, appendToFile);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при записи вещественных чисел в файл: " + doublesFile.getAbsolutePath(), e);
            System.err.println("Критическая ошибка: Не удалось записать вещественные числа в файл " + doublesFile.getName() +
                    ". Дальнейшая работа может быть нестабильной.");
        }


        File stringsFile = new File(outputDirectory, filePrefix + "strings.txt");
        try {
            if (!strings.isEmpty()) {
                dataWriter.writeData(strings, stringsFile, appendToFile);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при записи строк в файл: " + stringsFile.getAbsolutePath(), e);
            System.err.println("Критическая ошибка: Не удалось записать строки в файл " + stringsFile.getName() +
                    ". Дальнейшая работа может быть нестабильной.");
        }

        // Сбор статистики
        Statistics integerStatistics = integerStatisticsCollector.collect(integers);
        Statistics doubleStatistics = doubleStatisticsCollector.collect(doubles);
        Statistics stringStatistics = stringStatisticsCollector.collect(strings);

        return new StatisticsResult(integerStatistics, doubleStatistics, stringStatistics);
    }

    static class StatisticsResult {
        public Statistics integerStatistics;
        public Statistics doubleStatistics;
        public Statistics stringStatistics;

        public StatisticsResult(Statistics integerStatistics, Statistics doubleStatistics, Statistics stringStatistics) {
            this.integerStatistics = integerStatistics;
            this.doubleStatistics = doubleStatistics;
            this.stringStatistics = stringStatistics;
        }
    }
}