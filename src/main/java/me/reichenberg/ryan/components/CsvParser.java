package me.reichenberg.ryan.components;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvParser {

    /**
     * Read csv line by line and store it in a list
     * @param filePath
     * @return List of csv rows
     * @throws Exception
     */
    public List<String[]> parse(String filePath) throws Exception {
        // Try with resources to clean up afterwards
        try(CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        } catch (Exception e) {
           throw e;
        }
    }

    /**
     * Parse csv and convert it to list of objects
     * @param filePath
     * @param clazz
     * @param <T>
     * @return List of objects
     * @throws IOException
     */
    public <T> List<T> parse(String filePath, Class clazz) throws IOException {
        return new CsvToBeanBuilder(new FileReader(filePath))
                .withType(clazz).build().parse();
    }
}
