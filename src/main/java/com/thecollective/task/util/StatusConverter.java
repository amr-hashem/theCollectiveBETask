package com.thecollective.task.util;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.thecollective.task.bean.GeneratorStatus;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Objects;

public class StatusConverter extends AbstractBeanField {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        try {
            if(Objects.isNull(value)) {
                return null;
            }else {
                return EnumUtils.findEnumInsensitiveCase(GeneratorStatus.class, value);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
