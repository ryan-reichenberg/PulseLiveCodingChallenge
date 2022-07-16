package me.reichenberg.ryan.components;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvParser {

    public List<String[]> parse(String filePath) throws Exception {
        try(CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        } catch (Exception e) {
           throw e;
        }
    }

    public <T> List<T> parse(String filePath, Class clazz) throws IOException {
        return  new CsvToBeanBuilder(new FileReader(filePath))
                .withType(clazz).build().parse();
    }
}
