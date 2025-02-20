package com.zuev;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Класс, реализующий запись данных в файл
class DefaultDataWriter implements DataWriter {
    private static final Logger logger = Logger.getLogger(DefaultDataWriter.class.getName());
    @Override
    public <T> void writeData(List<T> data, File file, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при записи в файл: " + file.getAbsolutePath(), e);
            throw e;
        }
    }
}