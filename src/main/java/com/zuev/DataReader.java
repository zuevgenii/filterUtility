package com.zuev;
import java.io.IOException;
import java.util.List;
// Интерфейс для чтения данных из файла
interface DataReader {
    List<String> readLines(String filePath) throws IOException;
}
