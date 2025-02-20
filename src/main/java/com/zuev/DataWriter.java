package com.zuev;
import java.io.File;
import java.io.IOException;
import java.util.List;
// Интерфейс для записи данных в файл
interface DataWriter {
    <T> void writeData(List<T> data, File file, boolean append) throws IOException;
}