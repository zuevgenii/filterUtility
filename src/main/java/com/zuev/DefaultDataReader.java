package com.zuev;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Класс, реализующий чтение данных из файла
class DefaultDataReader implements DataReader {

    private static final Logger logger = Logger.getLogger(DefaultDataReader.class.getName());

    @Override
    public List<String> readLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при чтении файла: " + filePath, e);
            throw e;
        }
        return lines;
    }
}
