package com.example.backendassignment.helper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public interface GenericCsvHelper<T> {

    String[] headers();

    default List<T> readFile(MultipartFile file) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(',')
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {
            List<CSVRecord> records = csvParser.getRecords();
            List<T> dtoList = new ArrayList<>();
            for (CSVRecord record : records) {
                dtoList.add(createInstance(record));
            }
            return dtoList;

        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }


    T createInstance(CSVRecord csvRecord);
}
