package com.example.backendassignment.enums;

import com.example.backendassignment.entity.Data;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DataEnum implements GenericEnum<Data> {

    SOURCE("source", Data::setSource),
    CODE_LIST_CODE("codeListCode", Data::setCodeListCode),
    CODE("code", Data::setCode);

    private final String value;
    private final BiConsumer<Data, String> dtoSetterFunction;

    DataEnum(String value, BiConsumer<Data, String> dtoSetterFunction) {
        this.value = value;
        this.dtoSetterFunction = dtoSetterFunction;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public BiConsumer<Data, String> getDtoSetterFunction() {
        return dtoSetterFunction;
    }

    public static List<String> getValues() {
        return Stream.of(values())
                .map(DataEnum::getValue)
                .collect(Collectors.toList());
    }
}
