package com.asgarov.aktienVerwaltung.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {

    public static final String PATH_TO_RESOURCES = "src/resources/";
    private static final String CSV_EXTENTION = ".csv";

    public static List<String> readCSV(String fileName) throws IOException {
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_TO_RESOURCES + fileName + CSV_EXTENTION
        ))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }

        return records.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

}
