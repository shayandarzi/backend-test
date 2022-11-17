package com.example.backendassignment.enums;

import org.apache.commons.csv.CSVRecord;

import java.util.function.BiConsumer;

public interface GenericEnum<T> {
    String getValue();

    BiConsumer<T, String> getDtoSetterFunction();

    default void setDto(T object, CSVRecord csvRecord) {
        if (getDtoSetterFunction() != null) {

            getDtoSetterFunction().accept(object, csvRecord.get(getValue()));
        }

    }
}
