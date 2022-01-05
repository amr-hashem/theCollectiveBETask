package com.thecollective.task.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.thecollective.task.dto.PlantDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Slf4j
public class CommonUtil {

    private static final String SAMPLE_DATA = "src/main/resources/sample_data_values.csv";

    private static final int LINES_TO_SKIP = 1;


    public static List<PlantDTO> preloadExcelDataIntoBean() {
        try {

            CSVReader reader = new CSVReaderBuilder(new FileReader(SAMPLE_DATA))
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withSkipLines(LINES_TO_SKIP)
                    .build();
            CsvToBean<PlantDTO> csvReader = new CsvToBeanBuilder(reader)
                    .withType(PlantDTO.class)
                    .build();

            List<PlantDTO> plantDTOS = csvReader.parse();

            return plantDTOS;

        }catch (FileNotFoundException e) {
            log.error("File Not found!", e);
        }
        return null;
    }
}
