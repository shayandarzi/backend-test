package com.example.backendassignment.service;

import com.example.backendassignment.entity.Data;
import com.example.backendassignment.repository.DataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DataService {

    private final DataRepository dataRepository;


    public void parseCsvFile(MultipartFile csvFile) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(',')
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, csvFormat)) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                createData(csvRecord, formatter);
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
        }
    }

    private void createData(CSVRecord csvRecord, SimpleDateFormat formatter) {
        Data data = new Data();
        data.setSource(csvRecord.get("source"));
        data.setCodeListCode(csvRecord.get("codeListCode"));
        data.setCode(csvRecord.get("code"));
        data.setDisplayValue(csvRecord.get("displayValue"));
        data.setLongDescription(csvRecord.get("longDescription"));
        if (csvRecord.get("sortingPriority") != null && !csvRecord.get("sortingPriority").isEmpty()) {
            data.setSortingPriority(Integer.valueOf(csvRecord.get("sortingPriority")));
        }
        try {
            data.setFromDate(formatter.parse(csvRecord.get("fromDate")));
            if (csvRecord.get("toDate") != null && !csvRecord.get("toDate").isEmpty()) {
                data.setToDate(formatter.parse(csvRecord.get("toDate")));
            }
        } catch (ParseException e) {
            log.error("could not parse date format");
        }
        dataRepository.save(data);
    }

    public List<Data> getAllData() {
        return dataRepository.findAll();
    }

    public Data getData(String code) {
        return dataRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Data not found."));
    }

    public void deleteData(String code) {
        Data data = dataRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Data not found."));
        dataRepository.delete(data);
    }
}

