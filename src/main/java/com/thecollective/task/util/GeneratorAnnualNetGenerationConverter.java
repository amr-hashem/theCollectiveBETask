package com.thecollective.task.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.io.IOException;
import java.util.Objects;

public class GeneratorAnnualNetGenerationConverter extends AbstractBeanField {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(Objects.isNull(value)) {
                return null;
            }else {
                String getOnlyNumbers = value.replaceAll("\\D+","");
                return objectMapper.readValue(getOnlyNumbers, Long.class);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
