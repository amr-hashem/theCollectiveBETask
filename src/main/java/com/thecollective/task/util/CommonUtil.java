package com.thecollective.task.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.thecollective.task.dto.PlantDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class CommonUtil {

    private static final String SAMPLE_DATA = "data.csv";

    private static final int LINES_TO_SKIP = 1;


    public static List<PlantDTO> preloadExcelDataIntoBean() {
        try {
            Resource resource = new ClassPathResource(SAMPLE_DATA);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVReader reader = new CSVReaderBuilder(bufferedReader)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    .withSkipLines(LINES_TO_SKIP)
                    .build();
            CsvToBean<PlantDTO> csvReader = new CsvToBeanBuilder(reader)
                    .withType(PlantDTO.class)
                    .build();

            List<PlantDTO> plantDTOS = csvReader.parse();

            return plantDTOS;

        }catch (IOException e) {
            log.error("File Not found!", e);
        }
        return null;
    }
}
