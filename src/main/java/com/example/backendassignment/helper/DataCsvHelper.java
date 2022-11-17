package com.example.backendassignment.helper;

import com.example.backendassignment.entity.Data;
import com.example.backendassignment.enums.DataEnum;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;

public class DataCsvHelper implements GenericCsvHelper<Data> {
    @Override
    public String[] headers() {
        return DataEnum.getValues().toArray(new String[0]);
    }

    @Override
    public Data createInstance(CSVRecord csvRecord) {
        Data data = new Data();
        Arrays.stream(DataEnum.values()).forEach(dataEnum -> {
            dataEnum.setDto(data, csvRecord);
        });
        return data;
    }
}
