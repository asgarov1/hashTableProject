package com.asgarov.aktienVerwaltung.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    public static final String PATH_TO_RESOURCES = "src/resources/";
    private static final String CSV_EXTENTION = ".csv";

    /**
     * Method to read .csv file and returns a list of lines read
     * @param fileName
     * @return
     * @throws IOException
     */
    public static List<String> readCSV(String fileName) throws IOException {
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader(PATH_TO_RESOURCES + fileName + CSV_EXTENTION))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }

        return records;
    }

}
